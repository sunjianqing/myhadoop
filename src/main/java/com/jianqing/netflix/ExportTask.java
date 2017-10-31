package com.jianqing.netflix;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroSchemaConverter;
import org.apache.parquet.avro.AvroWriteSupport;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.schema.MessageType;

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
        File avroScheme = new File("src/main/avro/user.avsc");
        File avroData  = new File("src/main/resources/avro/users.avro");
        try {
            Schema avroSchema = new Schema.Parser().parse(avroScheme);
            // generate the corresponding Parquet schema
            MessageType parquetSchema = new AvroSchemaConverter().convert(avroSchema);

            // create a WriteSupport object to serialize your Avro objects
            AvroWriteSupport writeSupport = new AvroWriteSupport(parquetSchema, avroSchema);

            // choose compression scheme
            CompressionCodecName compressionCodecName = CompressionCodecName.UNCOMPRESSED;

            // set Parquet file block size and page size values
            int blockSize = 256 * 1024 * 1024;
            int pageSize = 64 * 1024;

            String outputFilename = "src/main/resources/avro/testuser";
            Path outputPath = new Path(outputFilename);

            // the ParquetWriter object that will consume Avro GenericRecords
            ParquetWriter parquetWriter = new ParquetWriter(outputPath,
                    writeSupport, compressionCodecName, blockSize, pageSize);

            GenericDatumReader<GenericData.Record> datum = new GenericDatumReader<GenericData.Record>();

            DataFileReader<GenericData.Record> reader = new DataFileReader<GenericData.Record>(avroData, datum);

            GenericData.Record record = new GenericData.Record(reader.getSchema());
            while (reader.hasNext()) {
                GenericData.Record next = reader.next(record);
                System.out.println(next);
                parquetWriter.write(next);
            }
            parquetWriter.close();
            reader.close();


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
