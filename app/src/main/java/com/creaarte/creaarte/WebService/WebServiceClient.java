package com.creaarte.creaarte.WebService;

import android.util.Log;
import android.webkit.MimeTypeMap;

import com.creaarte.creaarte.Controllers.AppCreaarte;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WebServiceClient {

    private static final String LINE_FEED = "\r\n";

    /**
     * URL base de todos los WebServices de la aplicacion. Cambiar a
     * conveniencia.
     */
    private static final String WS_URL = AppCreaarte.BASE_URL;

    /**
     * El metodo http utilizado para la peticion.
     */
    private final RequestMethod method;

    /**
     * La direccion del servidor con el que se desea comunicar.
     */
    private String url;

    /**
     * Delimitador usado para distinguir entre los parametros enviados al
     * servidor, cuando se utiliza Content-type: multipart/form-data.
     */
    private String boundary;

    /**
     * Mapa que guarda los parametros textuales que se deseen enviar.
     */
    private Map<String, String> params;

    /**
     * Mapa que guarda los archivos que se deseen enviar.
     */
    private Map<String, File> files;

    /**
     * Objeto que representa la conexion HTTP al url especificado.
     */
    private HttpURLConnection con;

    /**
     * OutputStream obtenido de HTTPUrlConnection para escribir la informacion
     * dada
     */
    private OutputStream out;

    private PrintWriter writer;

    private BufferedReader reader;

    private int responseCode = -1;

    /**
     * Crea una instancia con la direccion especificada.
     *
     * @param method
     *            el verbo HTTP a usar
     * @param url
     *            la direccion relativa del webservice, empezando con "/"
     */
    public WebServiceClient(RequestMethod method, String url) {
        this.method = method;
        this.url = WS_URL + url;
        this.params = new HashMap<String, String>();
        this.files = new HashMap<String, File>();
    }

    /**
     * Agrega un parametro para ser enviado al llamar execute.
     */

    public void addTextParam(String key, String value) {
        params.put(key, value);
    }

    /**
     * Agrega un archivo para ser enviado al llamar execute(). Actualmente solo
     * se soporta el envio de imagenes, y se infiere a partir de la extension
     * del archivo.
     */

    public void addFileParam(String key, File file) {
        files.put(key, file);
    }

    /**
     * Lo mismo que llamar webServiceClient.execute(null)
     *
     *            el metodo http a utilizar (GET o POST)
     * @return un string con la respuesta del servidor
     * @throws IOException
     */
    public String execute() throws IOException {
        return execute(null);
    }

    /**
     * El codigo HTTP con el que respondio el servidor
     *
     * @return el codigo de respuestar
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     *
     *            el metodo http a utilizar (GET o POST)
     * @param listener
     *            implementacion de interfaz WebService que define el codigo a
     *            llamar cada vez que el OuputStream escribe bytes al servidor
     * @return
     * @throws IOException
     */
    public String execute(final ProgressListener listener) throws IOException {
        String result = null;
        try {
            if (method.equals(RequestMethod.GET) && !params.isEmpty())
                url += "?" + appendGetParams();

            System.setProperty("http.keepAlive", "false");
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setUseCaches(false);

            con.setDoOutput(method == RequestMethod.POST);
            con.setDoInput(true);

            if (method == RequestMethod.POST) {
                boundary = "==" + System.currentTimeMillis() + "==";
                String contentType = files.isEmpty() ? "application/x-www-form-urlencoded"
                        : "multipart/form-data; boundary=" + boundary;
                con.setRequestProperty("Content-Type", contentType);
            }

            con.connect();

            out = con.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(out), true);

            if (method == RequestMethod.POST) {
                if (files.isEmpty()) {
                    String params = appendGetParams();
                    writer.append(params);
                    writer.flush();
                } else {
                    final long totalBytes = calculateContentLength();

                    if (listener != null) {
                        out = new BufferedOutputStream(out) {
                            long sent;

                            @Override
                            public void write(byte[] buf) throws IOException {
                                super.write(buf);
                                sent += buf.length;
                                listener.publish(sent, totalBytes);
                            }
                        };
                    }

                    writeTextParams();
                    writeFileParams();
                    finishMultipart();
                }
            }

            // deberia ir o no?
            writer.close();
            result = getResponse();
        } catch (IOException e) {
            throw e;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
            if (con != null) {
                con.disconnect();
            }
        }

        return result;
    }

    /* Comienzan metodos auxiliares */
    /**
     * Construye un String con los parametros formateados como si fueran a ir en
     * el url. Se utiliza para una peticion GET, o para una peticion POST sin
     * archivos (pues utiliza application/x-www-form-urlencoded)
     *
     *            el url base al que se va a concatenar. Vacio para POST.
     * @return la cadena con los parametros concatenados
     */
    private String appendGetParams() {
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        for (String key : params.keySet()) {
            sb.append(key);
            sb.append("=");
            sb.append(params.get(key));

            if (++cnt != params.size())
                sb.append("&");
        }

        return sb.toString();
    }

    /**
     * Calcula los bytes que van a ser enviados, a partir de los parametros y
     * los archivos. Este metodo solo se ejecuta cuado es una peticion POST.
     */
    private long calculateContentLength() {
        long total = 0L;

        for (String k : params.keySet())
            total += params.get(k).length();

        for (String k : files.keySet())
            total += files.get(k).length();

        return total;
    }

    /**
     * Escribe los parametros textuales con el formato multipart. El metodo no
     * se ejecuta si no se pasaron archivos.
     */
    private void writeTextParams() {
        for (String paramName : params.keySet()) {
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append(
                    "Content-Disposition: form-data; name=\"" + paramName
                            + "\"").append(LINE_FEED);
            writer.append(LINE_FEED);
            String value = params.get(paramName);
            writer.append(value).append(LINE_FEED);
            writer.flush();
        }
    }

    /**
     * Escribe los archivos en formato multipart. El tipo mime del archivo es
     * inferido a partir de la extension de este, asi que se debe asegurar que
     * se envie un archivo con una extension valida.
     */
    private void writeFileParams() throws IOException {
        for (String paramName : files.keySet()) {
            File file = files.get(paramName);
            String filename = file.getName();
            String mime;
            try {
                String ext = filename.substring(filename.lastIndexOf(".") + 1)
                        .toLowerCase();
                MimeTypeMap mimemap = MimeTypeMap.getSingleton();
                mime = mimemap.getMimeTypeFromExtension(ext);
                if (mime == null)
                    throw new UnknownExtensionException(ext);
            } catch (IndexOutOfBoundsException e) {
                throw new UnknownExtensionException("");
            }

            writer.append("--" + boundary).append(LINE_FEED);
            writer.append(
                    "Content-Disposition: form-data; name=\"" + paramName
                            + "\"; filename=\"" + filename + "\"").append(
                    LINE_FEED);
            writer.append("Content-Type: " + mime).append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            InputStream fin = new FileInputStream(file);
            byte[] buf = new byte[2048];
            while (fin.read(buf) != -1) {
                out.write(buf);
                out.flush();
            }
            out.flush();
            fin.close();

            writer.append(LINE_FEED);
            writer.flush();
        }
    }

    /**
     * De acuerdo al formato mutipart, asi se delimita el fin de la entrada.
     */
    private void finishMultipart() throws IOException {
        writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.flush();
    }

    /**
     * Obtiene la respuesta del servidor mediante el InputStream del
     * HTTPUrlConnection.
     */
    private String getResponse() {
        StringBuilder response = new StringBuilder();
        try {
            responseCode = con.getResponseCode();
            Log.i(this.getClass().getSimpleName(), "Call to " + url + " gave "
                    + responseCode);
            reader = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null)
                response.append(line);

        } catch (IOException e) {
            reader = new BufferedReader(new InputStreamReader(
                    con.getErrorStream()));
            String line = null;
            try {
                while ((line = reader.readLine()) != null)
                    response.append(line);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return response.toString();
    }

    /**
     * Metodos HTTP soportados por esta clase.
     *
     * @author Gustavo
     */
    public enum RequestMethod {
        GET, POST
    }
    /**
     * Interfaz donde se va a reportar el progreso de la subida de los archivos.
     *
     * @author Gustavo
     *
     */
    public interface ProgressListener {
        public void publish(long sent, long total);
    }

    /**
     * Excepcion que se lanza si el archivo enviado no tiene la extensi�n
     * necesaria.
     *
     * @author Gustavo
     */
    public class UnknownExtensionException extends RuntimeException {
        private static final long serialVersionUID = 1;

        public UnknownExtensionException(String ext) {
            super("Unknown extension " + ext);
        }
    }

}
