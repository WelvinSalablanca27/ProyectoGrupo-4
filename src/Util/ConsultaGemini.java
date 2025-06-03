/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import okhttp3.*;
import org.json.JSONObject;

/**
 *
 * @author welvi
 */
public class ConsultaGemini {

    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";
    private static final String API_KEY = "AIzaSyCBH7fdN76PWQX0e1IDW9n8HzOwHnDlrpI"; // Reemplaza con tu clave de API de Gemini

    public String generarConsultaSQL(String consultaNatural, String esquemaBD) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String prompt = """
            Genera una consulta SQL válida para una base de datos MySQL con las siguientes tablas y relaciones:
            %s
            Instrucciones:
            - Usa solo las columnas listadas en cada tabla.
            - Respeta el orden de los atributos de cada tabla según el esquemade la base de datos.
            - Asegúrate de que los JOINs sean correctos y utilicen las claves foráneas especificadas.
            - Si se solicita información de múltiples tablas, usa JOINs explícitos.
            - No generes subconsultas complejas ni funciones avanzadas a menos que sean explícitamente solicitadas.
            - Devuelve la consulta SQL en una sola línea, sin saltos de línea, comillas triples, ni formato adicional.
            Pregunta del usuario: "%s"
            """.formatted(esquemaBD, consultaNatural);

        JSONObject json = new JSONObject();
        json.put("contents", new JSONObject().put("parts", new JSONObject().put("text", prompt)));
        json.put("generationConfig", new JSONObject().put("response_mime_type", "text/plain"));

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(API_URL + "?key=" + API_KEY)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Error en la solicitud a Gemini: " + response.code());
            }
            String respuesta = response.body().string();
            return new JSONObject(respuesta).getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");
        }
    }

    // Método para obtener el esquema de la base de datos
    public String obtenerEsquemaBD() {
        return """
          Table Cliente {
              id_Cliente int [pk, increment]
              Nombre1 varchar(30)
              Nombre2 varchar(30)
              Apellido1 varchar(30)
              Apellido2 varchar(30)
              Direccion varchar(60)
              Telefono varchar(8)
            }
            
            Table Producto {
              id_Producto int [pk, increment]
              Nombre_Prod varchar(30)
              Tipo_Prod varchar(20)
              Existencia_Prod decimal(6,2)
              Precio_Venta decimal(10,2)
              Precio_Costo decimal(10,2)
              Fe_caducidad date
            }
            
            Table Proveedor {
              id_Proveedor int [pk, increment]
              Nombre_Proveedor varchar(30)
              Telefono varchar(8)
              Tipo_distribuidor varchar(30)
            }
            
            Table Compra {
              id_compra int [pk, increment]
              id_Proveedor int
              Fe_compra date
            total_compra float
            }
            
            Table DetalleCompra {
              id_DetalleCompra int [pk, increment]
              id_Producto int
              id_compra int
              Fe_Ingresado date
              Fe_caducidad date
              Precio float
              cantidad int
            
            }
            
            Table Venta {
              id_ventas int [pk, increment]
              id_Cliente int
              Cantidad_Pro int
              Fe_Venta date
              
            }
            
            Table Detalles_venta {
              id_DetalleVenta int [pk, increment]
              id_Producto int
              id_Venta int
              Cantidad_Producto int
              Precio decimal(10,2)
            
            }
               
            Ref: "Cliente"."id_Cliente" -> "Venta"."id_ventas"
            Ref: "Venta"."id_ventas" -> "Detalles_venta"."id_Venta"       
            Ref: "Detalles_venta"."id_Producto" -> "Producto"."id_Producto"            
            Ref: "Producto"."Existencia_Prod" -> "DetalleCompra"."Fe_Ingresado"          
            Ref: "Compra"."id_compra" -> "DetalleCompra"."id_DetalleCompra"
            Ref: "Proveedor"."id_Proveedor" -> "Compra"."id_Proveedor"
            """;
    }

    // Validación de consultas SQL
    public boolean esConsultaSegura(String sql) {
        String consulta = sql.toLowerCase().trim();
        return consulta.startsWith("select")
                && !consulta.contains("drop")
                && !consulta.contains("update")
                && !consulta.contains("insert");
    }

}
