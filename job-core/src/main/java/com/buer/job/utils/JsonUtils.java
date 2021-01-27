package com.buer.job.utils;

import com.buer.job.exception.JobException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class JsonUtils {
  private static final ObjectMapper mapper = new ObjectMapper();

  static {
    mapper.registerModule(new JodaModule());
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  }

  public static <M> M from(String value, Class<M> clazz) {
    try {
      return mapper.readValue(value, clazz);
    } catch (IOException e) {
      log.warn("Deserialize error. clazz: {}, val: {}", clazz.getSimpleName(), value, e);
    }
    return null;
  }

  public static <M> M fromOrException(String value, Class<M> clazz) {
    M res = from(value, clazz);
    if (res == null) {
      throw JobException.error("deserialize error. clazz: {}, val: {}", clazz, value);
    }
    return res;
  }

  public static <M> M from(String value, TypeReference<M> clazz) {
    try {
      return mapper.readValue(value, clazz);
    } catch (IOException e) {
      log.warn("Deserialize error. type_ref:{}, value:{}", clazz.getType().getTypeName(), value, e);
    }
    return null;
  }

  public static <M> M fromOrException(String value, TypeReference<M> typeRef) {
    M res = from(value, typeRef);
    if (res == null) {
      throw JobException.error("deserialize error. type_ref:{}, value:{} ", typeRef, value);
    }
    return res;
  }

  public static <M> M from(InputStream inputStream, Class<M> clazz) {
    try {
      return mapper.readValue(inputStream, clazz);
    } catch (IOException e) {
      log.warn("Deserialize inputStream error. clazz: {}", clazz, e);
      return null;
    }
  }

  public static <M> M fromOrException(InputStream inputStream, Class<M> clazz) {
    M res = from(inputStream, clazz);
    if (res == null) {
      throw JobException.error("deserialize inputStream error. clazz: {}", clazz);
    }
    return res;
  }

  public static <M> M from(InputStream inputStream, TypeReference<M> typeRef) {
    try {
      return mapper.readValue(inputStream, typeRef);
    } catch (IOException e) {
      log.warn("Deserialize inputStream error. type_ref: {}", typeRef, e);
      return null;
    }
  }

  public static <M> M fromOrException(InputStream inputStream, TypeReference<M> typeRef) {
    M res = from(inputStream, typeRef);
    if (res == null) {
      throw JobException.error("deserialize inputStream error. typeRef: {}", typeRef);
    }
    return res;
  }

  public static <M> M convertOrException(Object value, Class<M> clazz) {
    M res = mapper.convertValue(value, clazz);
    if (res == null) {
      throw JobException.error("convertOrException error. clazz: {}, val: {}", clazz, value);
    }
    return res;
  }

  public static <M> M convertOrException(Object value, TypeReference<M> typeRef) {
    M res = mapper.convertValue(value, typeRef);
    if (res == null) {
      throw JobException.error("convertOrException error. type_ref:{}, value:{} ", typeRef, value);
    }
    return res;
  }

  public static JsonNode from(File file) {
    try {
      return mapper.readTree(file);
    } catch (IOException e) {
      log.warn("Deserialize error. file:{}", file.getName(), e);
      return null;
    }
  }

  public static JsonNode fromOrException(File file) {
    JsonNode jsonNode = from(file);
    if (jsonNode == null) {
      throw JobException.error("deserialize error. file_path: {}", file.getPath());
    }
    return jsonNode;
  }

  public static String toString(Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      log.warn("Serialize error. obj:{},", obj, e);
      return null;
    }
  }

  public static void writeToOutputStream(OutputStream stream, Object obj) {
    try {
      mapper.writeValue(stream, obj);
    } catch (IOException e) {
      log.warn("Serialize to outputStream error. clazz:{}", obj.getClass().getSimpleName(), e);
    }
  }

  public static ObjectNode createObjectNode() {
    return mapper.createObjectNode();
  }

  public static ArrayNode createArrayNode() {
    return mapper.createArrayNode();
  }

  public static JsonNode readTree(String value) {
    try {
      return mapper.readTree(value);
    } catch (IOException e) {
      throw JobException.error("deserialize error. val: {}", value);
    }
  }
}
