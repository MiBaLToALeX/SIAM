package com.mibaltoalex.siam;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * El asistente inteligente SIAM
 *
 * @author Miguel J. Carmona (MIBALTOALEX)
 */
public final class SIAM {

    //java -jar app -DSIAM_KEY="mikey" -DSIAM_ENDPOINT="boing"
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Config config = new Config();
            System.out.print("> ");
            while (sc.hasNextLine()) {
                final String input = sc.nextLine();
                if("$exit".equals(input)) {
                    System.out.println("< Bye :D >");
                    System.exit(0);
                }
                String payload = gson.toJson(Prompt.builder().inputs(input).build());
                HttpResponse<String> resultado = Unirest.post("")
                        .header("Accept", "application/json")
                        .header("Authorization", "Bearer " + config.getSiamKey())
                        .body(payload).asString();
                if (resultado.isSuccess()) {
                    showResponse(resultado);
                } else {
                    System.err.println(resultado.getBody());
                }
                System.out.print("> ");
            }
        } catch (Throwable ignored) {
            System.err.println("La configuracion parece ser incorrecta");
        }
    }

    private static void showResponse(HttpResponse<String> resultado) {
        Type respuestasListType = new TypeToken<ArrayList<Response>>() {
        }.getType();
        ArrayList<Response> respuestaArray = gson.fromJson(resultado.getBody(), respuestasListType);
        respuestaArray.stream().distinct().forEach(respuesta -> System.out.println(respuesta.generated_text));
    }
}
