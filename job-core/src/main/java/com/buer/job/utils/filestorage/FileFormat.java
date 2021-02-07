package com.buer.job.utils.filestorage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;

public enum FileFormat {
  @JsonProperty("pdf")
  PDF(ImmutableSet.of("pdf")),
  ;

  public ImmutableSet<String> extensions;

  FileFormat(ImmutableSet<String> extensions) {
    this.extensions = extensions;
  }

  public static FileFormat fromExtensionOrNull(String extension) {
    for (FileFormat format : FileFormat.values()) {
      if (format.extensions.contains(extension)) {
        return format;
      }
    }
    return null;
  }
}
