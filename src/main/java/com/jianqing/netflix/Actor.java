/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.jianqing.netflix;

import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Actor extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 4997558563471981606L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Actor\",\"namespace\":\"com.jianqing.netflix\",\"fields\":[{\"name\":\"firstname\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"},{\"name\":\"lastname\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"},{\"name\":\"birthday\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"default\":\"\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public String firstname;
  @Deprecated public String lastname;
  @Deprecated public String birthday;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Actor() {}

  /**
   * All-args constructor.
   * @param firstname The new value for firstname
   * @param lastname The new value for lastname
   * @param birthday The new value for birthday
   */
  public Actor(String firstname, String lastname, String birthday) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.birthday = birthday;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public Object get(int field$) {
    switch (field$) {
    case 0: return firstname;
    case 1: return lastname;
    case 2: return birthday;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, Object value$) {
    switch (field$) {
    case 0: firstname = (String)value$; break;
    case 1: lastname = (String)value$; break;
    case 2: birthday = (String)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'firstname' field.
   * @return The value of the 'firstname' field.
   */
  public String getFirstname() {
    return firstname;
  }

  /**
   * Sets the value of the 'firstname' field.
   * @param value the value to set.
   */
  public void setFirstname(String value) {
    this.firstname = value;
  }

  /**
   * Gets the value of the 'lastname' field.
   * @return The value of the 'lastname' field.
   */
  public String getLastname() {
    return lastname;
  }

  /**
   * Sets the value of the 'lastname' field.
   * @param value the value to set.
   */
  public void setLastname(String value) {
    this.lastname = value;
  }

  /**
   * Gets the value of the 'birthday' field.
   * @return The value of the 'birthday' field.
   */
  public String getBirthday() {
    return birthday;
  }

  /**
   * Sets the value of the 'birthday' field.
   * @param value the value to set.
   */
  public void setBirthday(String value) {
    this.birthday = value;
  }

  /**
   * Creates a new Actor RecordBuilder.
   * @return A new Actor RecordBuilder
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Creates a new Actor RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Actor RecordBuilder
   */
  public static Builder newBuilder(Builder other) {
    return new Builder(other);
  }

  /**
   * Creates a new Actor RecordBuilder by copying an existing Actor instance.
   * @param other The existing instance to copy.
   * @return A new Actor RecordBuilder
   */
  public static Builder newBuilder(Actor other) {
    return new Builder(other);
  }

  /**
   * RecordBuilder for Actor instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Actor>
    implements org.apache.avro.data.RecordBuilder<Actor> {

    private String firstname;
    private String lastname;
    private String birthday;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.firstname)) {
        this.firstname = data().deepCopy(fields()[0].schema(), other.firstname);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.lastname)) {
        this.lastname = data().deepCopy(fields()[1].schema(), other.lastname);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.birthday)) {
        this.birthday = data().deepCopy(fields()[2].schema(), other.birthday);
        fieldSetFlags()[2] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing Actor instance
     * @param other The existing instance to copy.
     */
    private Builder(Actor other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.firstname)) {
        this.firstname = data().deepCopy(fields()[0].schema(), other.firstname);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.lastname)) {
        this.lastname = data().deepCopy(fields()[1].schema(), other.lastname);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.birthday)) {
        this.birthday = data().deepCopy(fields()[2].schema(), other.birthday);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'firstname' field.
      * @return The value.
      */
    public String getFirstname() {
      return firstname;
    }

    /**
      * Sets the value of the 'firstname' field.
      * @param value The value of 'firstname'.
      * @return This builder.
      */
    public Builder setFirstname(String value) {
      validate(fields()[0], value);
      this.firstname = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'firstname' field has been set.
      * @return True if the 'firstname' field has been set, false otherwise.
      */
    public boolean hasFirstname() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'firstname' field.
      * @return This builder.
      */
    public Builder clearFirstname() {
      firstname = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'lastname' field.
      * @return The value.
      */
    public String getLastname() {
      return lastname;
    }

    /**
      * Sets the value of the 'lastname' field.
      * @param value The value of 'lastname'.
      * @return This builder.
      */
    public Builder setLastname(String value) {
      validate(fields()[1], value);
      this.lastname = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'lastname' field has been set.
      * @return True if the 'lastname' field has been set, false otherwise.
      */
    public boolean hasLastname() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'lastname' field.
      * @return This builder.
      */
    public Builder clearLastname() {
      lastname = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'birthday' field.
      * @return The value.
      */
    public String getBirthday() {
      return birthday;
    }

    /**
      * Sets the value of the 'birthday' field.
      * @param value The value of 'birthday'.
      * @return This builder.
      */
    public Builder setBirthday(String value) {
      validate(fields()[2], value);
      this.birthday = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'birthday' field has been set.
      * @return True if the 'birthday' field has been set, false otherwise.
      */
    public boolean hasBirthday() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'birthday' field.
      * @return This builder.
      */
    public Builder clearBirthday() {
      birthday = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    public Actor build() {
      try {
        Actor record = new Actor();
        record.firstname = fieldSetFlags()[0] ? this.firstname : (String) defaultValue(fields()[0]);
        record.lastname = fieldSetFlags()[1] ? this.lastname : (String) defaultValue(fields()[1]);
        record.birthday = fieldSetFlags()[2] ? this.birthday : (String) defaultValue(fields()[2]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  private static final org.apache.avro.io.DatumWriter
    WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  private static final org.apache.avro.io.DatumReader
    READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
