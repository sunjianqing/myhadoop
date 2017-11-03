package com.jianqing.netflix;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.schema.MessageType;
import parquet.avro.AvroWriteSupport;

import java.io.File;
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
        File avroScheme = new File("src/main/avro/netflix/movie.avsc");
        File avroData  = new File("/tmp/movie");
        try {



        } catch (IOException e) {
            e.printStackTrace();
            return 1;
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
