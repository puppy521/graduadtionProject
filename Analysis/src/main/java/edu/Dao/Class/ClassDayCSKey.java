package edu.Dao.Class;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ClassDayCSKey implements WritableComparable<ClassDayCSKey> {
    @Override
    public int compareTo(ClassDayCSKey o) {
        return 0;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {

    }
}
