package com.jianqing.avro;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;

/**
 * Created by jianqingsun on 10/25/17.
 */
public class AvroDemo {
    public static void main(String[] args) throws IOException {
//        User user1 = new User();
//        user1.setName("Ethan");
//        user1.setFavoriteNumber(256);
//        //  Leave favorite color null
//
//        // Serialize user1 and user2 to disk
//        File file = new File("src/main/resources/avro/users.avro");
//        DatumWriter<User> userDatumWriter = new SpecificDatumWriter<>(User.class);
//        DataFileWriter<User> dataFileWriter = new DataFileWriter<>(userDatumWriter);
//        dataFileWriter.create(user1.getSchema(), file);
//        dataFileWriter.append(user1);
//        //dataFileWriter.append(user2);
//        //dataFileWriter.append(user3);
//        dataFileWriter.close();
//
//
//        // Deserialize Users from disk
//        DatumReader<User> userDatumReader = new SpecificDatumReader<>(User.class);
//        DataFileReader<User> dataFileReader =
//                new DataFileReader<>(file, userDatumReader);
//        User user = null;
//        while (dataFileReader.hasNext()) {
//            // Reuse user object by passing it to next(). This saves us from
//            // allocating and garbage collecting many objects for files with
//            // many items.
//            user = dataFileReader.next(user);
//            System.out.println(user);
//        }
    }
}
