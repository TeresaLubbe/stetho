// Copyright 2004-present Facebook. All Rights Reserved.

package com.facebook.stetho.websocket;

import javax.annotation.concurrent.ThreadSafe;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@ThreadSafe
class WriteHandler {
  private final BufferedOutputStream mBufferedOutput;

  public WriteHandler(OutputStream rawSocketOutput) {
    mBufferedOutput = new BufferedOutputStream(rawSocketOutput, 1024);
  }

  public synchronized void write(Frame frame, WriteCallback callback) {
    try {
      frame.writeTo(mBufferedOutput);
      mBufferedOutput.flush();
      callback.onSuccess();
    } catch (IOException e) {
      callback.onFailure(e);
    }
  }
}