package com.example.manipedi.DB;

import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;

public class NailPolishParser {
    public static ArrayList<NailPolish> parseArray(JsonReader jsonReader) throws IOException {
        ArrayList<NailPolish> results = new ArrayList();
        jsonReader.beginArray();

        while (jsonReader.hasNext()) {
            results.add(parseJson(jsonReader));
        }

        jsonReader.endArray();
        jsonReader.close();

        return results;
    }

    public static NailPolish parseJson(JsonReader jsonReader) throws IOException {
        Integer id = 0;
        String brand = "not found";
        String name = "not found";
        String description = "not found";
        String image = "";
        String apiUrl = "";

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            String key = jsonReader.nextName();
            switch (key) {
                case "id":
                    id = jsonReader.nextInt();
                    break;
                case "brand":
                    brand = jsonReader.nextString();
                    break;
                case "name":
                    name = jsonReader.nextString();
                    break;
                case "description":
                    description = jsonReader.nextString();
                    break;
                case "api_featured_image":
                    image = jsonReader.nextString();
                    break;
                case "product_api_url":
                    apiUrl = jsonReader.nextString();
                    break;
                default:
                    jsonReader.skipValue();
            }
        }

        jsonReader.endObject();

        return new NailPolish(id, brand, name, description, image, apiUrl);
    }
}
