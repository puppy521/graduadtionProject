package edu.Infomation.Student;

import edu.Infomation.enumObject.ThreeMeals;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDayTCS implements Writable, DBWritable {
    private String sid;
    private Date day;
    private ThreeMeals consumption_category;
    private int count;
    private float totalMoney;
    private float averageMoney;

    public StudentDayTCS() {
    }

    public StudentDayTCS(String sid, Date day, ThreeMeals consumption_category, int count, float totalMoney, float averageMoney) {
        this.sid = sid;
        this.day = day;
        this.consumption_category = consumption_category;
        this.count = count;
        this.totalMoney = totalMoney;
        this.averageMoney = averageMoney;
    }

    @Override
    public String toString() {
        return "StudentDayTCS{" +
                "sid='" + sid + '\'' +
                ", day=" + day +
                ", consumption_category='" + consumption_category + '\'' +
                ", count=" + count +
                ", totalMoney=" + totalMoney +
                ", averageMoney=" + averageMoney +
                '}';
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public ThreeMeals getConsumption_category() {
        return consumption_category;
    }

    public void setConsumption_category(ThreeMeals consumption_category) {
        this.consumption_category = consumption_category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public float getAverageMoney() {
        return averageMoney;
    }

    public void setAverageMoney(float averageMoney) {
        this.averageMoney = averageMoney;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.sid);
        Text.writeString(dataOutput, this.day.toString());
        dataOutput.writeUTF(this.consumption_category.getMeal());
        dataOutput.writeInt(this.count);
        dataOutput.writeFloat(this.totalMoney);
        dataOutput.writeFloat(this.averageMoney);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.sid = dataInput.readUTF();
        this.day = Date.valueOf(Text.readString(dataInput));
        this.consumption_category = ThreeMeals.valueOf(dataInput.readUTF());
        this.count = dataInput.readInt();
        this.totalMoney = dataInput.readFloat();
        this.averageMoney = dataInput.readFloat();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, this.sid);
        preparedStatement.setDate(2, this.day);
        preparedStatement.setString(3,this.consumption_category.getMeal());
        preparedStatement.setInt(4, this.count);
        preparedStatement.setFloat(5, this.totalMoney);
        preparedStatement.setFloat(6, this.averageMoney);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.sid = resultSet.getString(1);
        this.day = resultSet.getDate(2);
        this.consumption_category = ThreeMeals.valueOf(resultSet.getString(3));
        this.count = resultSet.getInt(4);
        this.totalMoney = resultSet.getFloat(5);
        this.averageMoney = resultSet.getFloat(6);
    }
}
