package com.bing.lan.heap;

import java.nio.ByteBuffer;

/**
 * Created by backend.
 * <p>
 * 详解java中的byte类型
 * https://www.cnblogs.com/zl181015/p/9435035.html
 */

public class HeapByteBufferTest {

  public static void main(String[] args) throws Exception {
    int capacity = 10 * 1024 * 1024;

    ByteBuffer heapByteBuffer = ByteBuffer.allocate(capacity);
    System.out.println("main(): " + heapByteBuffer);
    divider(heapByteBuffer);

    heapByteBuffer.putChar('a');
    divider(heapByteBuffer);

    heapByteBuffer.putInt(1);
    divider(heapByteBuffer);

    heapByteBuffer.putChar('b');
    divider(heapByteBuffer);

    byte b1 = heapByteBuffer.get();
    byte[] array = heapByteBuffer.array();
    array[0] = 12;

    System.out.println("main(): " + heapByteBuffer);
  }

  public static void divider(ByteBuffer byteBuffer) {
    System.out.println("divider(): " + byteBuffer);
    //byteBuffer.put((byte) 127);
  }
}
