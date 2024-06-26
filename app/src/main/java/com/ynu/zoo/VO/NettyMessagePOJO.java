// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: NettyMessage.proto

package com.ynu.zoo.VO;

public final class NettyMessagePOJO {
  private NettyMessagePOJO() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface NettyMessageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:NettyMessage)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 messageType = 1;</code>
     * @return The messageType.
     */
    int getMessageType();

    /**
     * <code>string messageBody = 2;</code>
     * @return The messageBody.
     */
    String getMessageBody();
    /**
     * <code>string messageBody = 2;</code>
     * @return The bytes for messageBody.
     */
    com.google.protobuf.ByteString
        getMessageBodyBytes();

    /**
     * <code>string sender = 6;</code>
     * @return The sender.
     */
    String getSender();
    /**
     * <code>string sender = 6;</code>
     * @return The bytes for sender.
     */
    com.google.protobuf.ByteString
        getSenderBytes();

    /**
     * <code>int32 roomID = 7;</code>
     * @return The roomID.
     */
    int getRoomID();

    /**
     * <code>int32 parameter1 = 3;</code>
     * @return The parameter1.
     */
    int getParameter1();

    /**
     * <code>int32 parameter2 = 4;</code>
     * @return The parameter2.
     */
    int getParameter2();

    /**
     * <code>string parameter3 = 5;</code>
     * @return The parameter3.
     */
    String getParameter3();
    /**
     * <code>string parameter3 = 5;</code>
     * @return The bytes for parameter3.
     */
    com.google.protobuf.ByteString
        getParameter3Bytes();
  }
  /**
   * Protobuf type {@code NettyMessage}
   */
  public  static final class NettyMessage extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:NettyMessage)
      NettyMessageOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use NettyMessage.newBuilder() to construct.
    private NettyMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private NettyMessage() {
      messageBody_ = "";
      sender_ = "";
      parameter3_ = "";
    }

    @Override
    @SuppressWarnings({"unused"})
    protected Object newInstance(
        UnusedPrivateParameter unused) {
      return new NettyMessage();
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private NettyMessage(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {

              messageType_ = input.readInt32();
              break;
            }
            case 18: {
              String s = input.readStringRequireUtf8();

              messageBody_ = s;
              break;
            }
            case 24: {

              parameter1_ = input.readInt32();
              break;
            }
            case 32: {

              parameter2_ = input.readInt32();
              break;
            }
            case 42: {
              String s = input.readStringRequireUtf8();

              parameter3_ = s;
              break;
            }
            case 50: {
              String s = input.readStringRequireUtf8();

              sender_ = s;
              break;
            }
            case 56: {

              roomID_ = input.readInt32();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return NettyMessagePOJO.internal_static_NettyMessage_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return NettyMessagePOJO.internal_static_NettyMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              NettyMessage.class, Builder.class);
    }

    public static final int MESSAGETYPE_FIELD_NUMBER = 1;
    private int messageType_;
    /**
     * <code>int32 messageType = 1;</code>
     * @return The messageType.
     */
    public int getMessageType() {
      return messageType_;
    }

    public static final int MESSAGEBODY_FIELD_NUMBER = 2;
    private volatile Object messageBody_;
    /**
     * <code>string messageBody = 2;</code>
     * @return The messageBody.
     */
    public String getMessageBody() {
      Object ref = messageBody_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        messageBody_ = s;
        return s;
      }
    }
    /**
     * <code>string messageBody = 2;</code>
     * @return The bytes for messageBody.
     */
    public com.google.protobuf.ByteString
        getMessageBodyBytes() {
      Object ref = messageBody_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        messageBody_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SENDER_FIELD_NUMBER = 6;
    private volatile Object sender_;
    /**
     * <code>string sender = 6;</code>
     * @return The sender.
     */
    public String getSender() {
      Object ref = sender_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        sender_ = s;
        return s;
      }
    }
    /**
     * <code>string sender = 6;</code>
     * @return The bytes for sender.
     */
    public com.google.protobuf.ByteString
        getSenderBytes() {
      Object ref = sender_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        sender_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int ROOMID_FIELD_NUMBER = 7;
    private int roomID_;
    /**
     * <code>int32 roomID = 7;</code>
     * @return The roomID.
     */
    public int getRoomID() {
      return roomID_;
    }

    public static final int PARAMETER1_FIELD_NUMBER = 3;
    private int parameter1_;
    /**
     * <code>int32 parameter1 = 3;</code>
     * @return The parameter1.
     */
    public int getParameter1() {
      return parameter1_;
    }

    public static final int PARAMETER2_FIELD_NUMBER = 4;
    private int parameter2_;
    /**
     * <code>int32 parameter2 = 4;</code>
     * @return The parameter2.
     */
    public int getParameter2() {
      return parameter2_;
    }

    public static final int PARAMETER3_FIELD_NUMBER = 5;
    private volatile Object parameter3_;
    /**
     * <code>string parameter3 = 5;</code>
     * @return The parameter3.
     */
    public String getParameter3() {
      Object ref = parameter3_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        parameter3_ = s;
        return s;
      }
    }
    /**
     * <code>string parameter3 = 5;</code>
     * @return The bytes for parameter3.
     */
    public com.google.protobuf.ByteString
        getParameter3Bytes() {
      Object ref = parameter3_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        parameter3_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    @Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (messageType_ != 0) {
        output.writeInt32(1, messageType_);
      }
      if (!getMessageBodyBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, messageBody_);
      }
      if (parameter1_ != 0) {
        output.writeInt32(3, parameter1_);
      }
      if (parameter2_ != 0) {
        output.writeInt32(4, parameter2_);
      }
      if (!getParameter3Bytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, parameter3_);
      }
      if (!getSenderBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 6, sender_);
      }
      if (roomID_ != 0) {
        output.writeInt32(7, roomID_);
      }
      unknownFields.writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (messageType_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, messageType_);
      }
      if (!getMessageBodyBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, messageBody_);
      }
      if (parameter1_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, parameter1_);
      }
      if (parameter2_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(4, parameter2_);
      }
      if (!getParameter3Bytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, parameter3_);
      }
      if (!getSenderBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, sender_);
      }
      if (roomID_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(7, roomID_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof NettyMessage)) {
        return super.equals(obj);
      }
      NettyMessage other = (NettyMessage) obj;

      if (getMessageType()
          != other.getMessageType()) return false;
      if (!getMessageBody()
          .equals(other.getMessageBody())) return false;
      if (!getSender()
          .equals(other.getSender())) return false;
      if (getRoomID()
          != other.getRoomID()) return false;
      if (getParameter1()
          != other.getParameter1()) return false;
      if (getParameter2()
          != other.getParameter2()) return false;
      if (!getParameter3()
          .equals(other.getParameter3())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + MESSAGETYPE_FIELD_NUMBER;
      hash = (53 * hash) + getMessageType();
      hash = (37 * hash) + MESSAGEBODY_FIELD_NUMBER;
      hash = (53 * hash) + getMessageBody().hashCode();
      hash = (37 * hash) + SENDER_FIELD_NUMBER;
      hash = (53 * hash) + getSender().hashCode();
      hash = (37 * hash) + ROOMID_FIELD_NUMBER;
      hash = (53 * hash) + getRoomID();
      hash = (37 * hash) + PARAMETER1_FIELD_NUMBER;
      hash = (53 * hash) + getParameter1();
      hash = (37 * hash) + PARAMETER2_FIELD_NUMBER;
      hash = (53 * hash) + getParameter2();
      hash = (37 * hash) + PARAMETER3_FIELD_NUMBER;
      hash = (53 * hash) + getParameter3().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static NettyMessage parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static NettyMessage parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static NettyMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static NettyMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static NettyMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static NettyMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static NettyMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static NettyMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static NettyMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static NettyMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static NettyMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static NettyMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(NettyMessage prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code NettyMessage}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:NettyMessage)
        NettyMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return NettyMessagePOJO.internal_static_NettyMessage_descriptor;
      }

      @Override
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return NettyMessagePOJO.internal_static_NettyMessage_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                NettyMessage.class, Builder.class);
      }

      // Construct using com.ynu.zoo.VO.NettyMessagePOJO.NettyMessage.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @Override
      public Builder clear() {
        super.clear();
        messageType_ = 0;

        messageBody_ = "";

        sender_ = "";

        roomID_ = 0;

        parameter1_ = 0;

        parameter2_ = 0;

        parameter3_ = "";

        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return NettyMessagePOJO.internal_static_NettyMessage_descriptor;
      }

      @Override
      public NettyMessage getDefaultInstanceForType() {
        return NettyMessage.getDefaultInstance();
      }

      @Override
      public NettyMessage build() {
        NettyMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public NettyMessage buildPartial() {
        NettyMessage result = new NettyMessage(this);
        result.messageType_ = messageType_;
        result.messageBody_ = messageBody_;
        result.sender_ = sender_;
        result.roomID_ = roomID_;
        result.parameter1_ = parameter1_;
        result.parameter2_ = parameter2_;
        result.parameter3_ = parameter3_;
        onBuilt();
        return result;
      }

      @Override
      public Builder clone() {
        return super.clone();
      }
      @Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.setField(field, value);
      }
      @Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.addRepeatedField(field, value);
      }
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof NettyMessage) {
          return mergeFrom((NettyMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(NettyMessage other) {
        if (other == NettyMessage.getDefaultInstance()) return this;
        if (other.getMessageType() != 0) {
          setMessageType(other.getMessageType());
        }
        if (!other.getMessageBody().isEmpty()) {
          messageBody_ = other.messageBody_;
          onChanged();
        }
        if (!other.getSender().isEmpty()) {
          sender_ = other.sender_;
          onChanged();
        }
        if (other.getRoomID() != 0) {
          setRoomID(other.getRoomID());
        }
        if (other.getParameter1() != 0) {
          setParameter1(other.getParameter1());
        }
        if (other.getParameter2() != 0) {
          setParameter2(other.getParameter2());
        }
        if (!other.getParameter3().isEmpty()) {
          parameter3_ = other.parameter3_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @Override
      public final boolean isInitialized() {
        return true;
      }

      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        NettyMessage parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (NettyMessage) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int messageType_ ;
      /**
       * <code>int32 messageType = 1;</code>
       * @return The messageType.
       */
      public int getMessageType() {
        return messageType_;
      }
      /**
       * <code>int32 messageType = 1;</code>
       * @param value The messageType to set.
       * @return This builder for chaining.
       */
      public Builder setMessageType(int value) {
        
        messageType_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 messageType = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearMessageType() {
        
        messageType_ = 0;
        onChanged();
        return this;
      }

      private Object messageBody_ = "";
      /**
       * <code>string messageBody = 2;</code>
       * @return The messageBody.
       */
      public String getMessageBody() {
        Object ref = messageBody_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          messageBody_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string messageBody = 2;</code>
       * @return The bytes for messageBody.
       */
      public com.google.protobuf.ByteString
          getMessageBodyBytes() {
        Object ref = messageBody_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          messageBody_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string messageBody = 2;</code>
       * @param value The messageBody to set.
       * @return This builder for chaining.
       */
      public Builder setMessageBody(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        messageBody_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string messageBody = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearMessageBody() {
        
        messageBody_ = getDefaultInstance().getMessageBody();
        onChanged();
        return this;
      }
      /**
       * <code>string messageBody = 2;</code>
       * @param value The bytes for messageBody to set.
       * @return This builder for chaining.
       */
      public Builder setMessageBodyBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        messageBody_ = value;
        onChanged();
        return this;
      }

      private Object sender_ = "";
      /**
       * <code>string sender = 6;</code>
       * @return The sender.
       */
      public String getSender() {
        Object ref = sender_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          sender_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string sender = 6;</code>
       * @return The bytes for sender.
       */
      public com.google.protobuf.ByteString
          getSenderBytes() {
        Object ref = sender_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          sender_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string sender = 6;</code>
       * @param value The sender to set.
       * @return This builder for chaining.
       */
      public Builder setSender(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        sender_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string sender = 6;</code>
       * @return This builder for chaining.
       */
      public Builder clearSender() {
        
        sender_ = getDefaultInstance().getSender();
        onChanged();
        return this;
      }
      /**
       * <code>string sender = 6;</code>
       * @param value The bytes for sender to set.
       * @return This builder for chaining.
       */
      public Builder setSenderBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        sender_ = value;
        onChanged();
        return this;
      }

      private int roomID_ ;
      /**
       * <code>int32 roomID = 7;</code>
       * @return The roomID.
       */
      public int getRoomID() {
        return roomID_;
      }
      /**
       * <code>int32 roomID = 7;</code>
       * @param value The roomID to set.
       * @return This builder for chaining.
       */
      public Builder setRoomID(int value) {
        
        roomID_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 roomID = 7;</code>
       * @return This builder for chaining.
       */
      public Builder clearRoomID() {
        
        roomID_ = 0;
        onChanged();
        return this;
      }

      private int parameter1_ ;
      /**
       * <code>int32 parameter1 = 3;</code>
       * @return The parameter1.
       */
      public int getParameter1() {
        return parameter1_;
      }
      /**
       * <code>int32 parameter1 = 3;</code>
       * @param value The parameter1 to set.
       * @return This builder for chaining.
       */
      public Builder setParameter1(int value) {
        
        parameter1_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 parameter1 = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearParameter1() {
        
        parameter1_ = 0;
        onChanged();
        return this;
      }

      private int parameter2_ ;
      /**
       * <code>int32 parameter2 = 4;</code>
       * @return The parameter2.
       */
      public int getParameter2() {
        return parameter2_;
      }
      /**
       * <code>int32 parameter2 = 4;</code>
       * @param value The parameter2 to set.
       * @return This builder for chaining.
       */
      public Builder setParameter2(int value) {
        
        parameter2_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 parameter2 = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearParameter2() {
        
        parameter2_ = 0;
        onChanged();
        return this;
      }

      private Object parameter3_ = "";
      /**
       * <code>string parameter3 = 5;</code>
       * @return The parameter3.
       */
      public String getParameter3() {
        Object ref = parameter3_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          parameter3_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string parameter3 = 5;</code>
       * @return The bytes for parameter3.
       */
      public com.google.protobuf.ByteString
          getParameter3Bytes() {
        Object ref = parameter3_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          parameter3_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string parameter3 = 5;</code>
       * @param value The parameter3 to set.
       * @return This builder for chaining.
       */
      public Builder setParameter3(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        parameter3_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string parameter3 = 5;</code>
       * @return This builder for chaining.
       */
      public Builder clearParameter3() {
        
        parameter3_ = getDefaultInstance().getParameter3();
        onChanged();
        return this;
      }
      /**
       * <code>string parameter3 = 5;</code>
       * @param value The bytes for parameter3 to set.
       * @return This builder for chaining.
       */
      public Builder setParameter3Bytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        parameter3_ = value;
        onChanged();
        return this;
      }
      @Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:NettyMessage)
    }

    // @@protoc_insertion_point(class_scope:NettyMessage)
    private static final NettyMessage DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new NettyMessage();
    }

    public static NettyMessage getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<NettyMessage>
        PARSER = new com.google.protobuf.AbstractParser<NettyMessage>() {
      @Override
      public NettyMessage parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new NettyMessage(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<NettyMessage> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<NettyMessage> getParserForType() {
      return PARSER;
    }

    @Override
    public NettyMessage getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_NettyMessage_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_NettyMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\022NettyMessage.proto\"\224\001\n\014NettyMessage\022\023\n" +
      "\013messageType\030\001 \001(\005\022\023\n\013messageBody\030\002 \001(\t\022" +
      "\016\n\006sender\030\006 \001(\t\022\016\n\006roomID\030\007 \001(\005\022\022\n\nparam" +
      "eter1\030\003 \001(\005\022\022\n\nparameter2\030\004 \001(\005\022\022\n\nparam" +
      "eter3\030\005 \001(\tB$\n\016com.ynu.zoo.VOB\020NettyMess" +
      "agePOJOH\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_NettyMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_NettyMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_NettyMessage_descriptor,
        new String[] { "MessageType", "MessageBody", "Sender", "RoomID", "Parameter1", "Parameter2", "Parameter3", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
