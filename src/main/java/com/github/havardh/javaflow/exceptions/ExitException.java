package com.github.havardh.javaflow.exceptions;

public class ExitException extends RuntimeException {
  public enum ErrorCode {
    COULD_NOT_PARSE_TYPE_MAP(1),
    COULD_NOT_PARSE_SOURCE_CODE(2),
    COULD_NOT_READ_FILE(3),
    COULD_NOT_RESOLVE_PATTERN(4),
    COULD_NOT_WRITE_OUTPUT(5);

    private final int code;

    ErrorCode(int code) {
      this.code = code;
    }

    public int getCode() {
      return code;
    }
  }

  private final ErrorCode errorCode;

  public ExitException(ErrorCode errorCode, Throwable cause) {
    super(errorCode.name(), cause);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
