package com.jianqing.netflix;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.schema.MessageType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;





/**
 * Created by jianqingsun on 10/28/17.
 * <p>
 * Export Data to HDFS.
 * Option: Raw HDFS Writer / Sqoop
 */
public class ExportTask implements TaskInterface {

    @Override
    public void init() {

    }

    @Override
    public int run() {
        File avroData  = new File("/tmp/movie");
        String newLine = System.getProperty("line.separator");

        DatumReader<MovieMetadata> movieDatumReader = new SpecificDatumReader<>(MovieMetadata.class);
        DataFileReader<MovieMetadata> dataFileReader =
                null;
        try {
            dataFileReader = new DataFileReader<>(avroData, movieDatumReader);
            MovieMetadata movie;
            FileWriter fw = new FileWriter("/tmp/moviejson");

            while (dataFileReader.hasNext()) {
                // Reuse user object by passing it to next(). This saves us from
                // allocating and garbage collecting many objects for files with
                // many items.
                movie = dataFileReader.next();
                fw.write(movie.toString() + newLine);
            }
            fw.close();
            dataFileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



        return 0;
    }

    @Override
    public int stop() {
        return 0;
    }

    @Override
    public void clean() {

    }

    public static void main(String[] args) {
        ExportTask task = new ExportTask();
        task.run();
    }
}
