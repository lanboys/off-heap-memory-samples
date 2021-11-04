package com.bing.lan.heap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Created by lb on 2021/11/4.
 */

public class DirectByteBufferTest {

  public static void main(String[] args) throws Exception {
    //testDirectByteBufferClean();

    int capacity = 10 * 1024 * 1024;
    ByteBuffer directByteBuffer = ByteBuffer.allocateDirect(capacity);
    divider(directByteBuffer);

    directByteBuffer.putChar('a');
    divider(directByteBuffer);

    directByteBuffer.putInt(1);
    divider(directByteBuffer);

    directByteBuffer.putChar('b');
    divider(directByteBuffer);

    byte b1 = directByteBuffer.get();
    IntBuffer intBuffer = directByteBuffer.asIntBuffer();
    System.out.println("main(): " + directByteBuffer);
  }

  public static void divider(ByteBuffer byteBuffer) {
    System.out.println("divider(): " + byteBuffer);
    //byteBuffer.put((byte) 127);
  }

  private static void testDirectByteBufferClean() throws Exception {
    // 分配堆外内存
    ByteBuffer directByteBuffer = ByteBuffer.allocateDirect(120 * 1024 * 1024);
    System.out.println("main(): " + directByteBuffer);

    //调用 clean直接清除，没包权限，用反射
    invokeBufferClean(directByteBuffer);

    ByteBuffer directByteBuffer2 = ByteBuffer.allocateDirect(200 * 1024 * 1024);
    System.out.println("main(): " + directByteBuffer2);

    // full gc 可以回收不再使用的堆外内存
    directByteBuffer2 = null;
    System.gc();//gc时，Reference Handler线程会对堆外内存进行清除（ 在Reference类253行，可以debug看看 ）

    System.out.println("main(): gc");

    DirectByteBufferTest test = new DirectByteBufferTest();
    synchronized (test) {
      test.wait();
    }
    System.out.println("main(): end");
  }

  /**
   * <p>
   * 反射报错
   * <p>
   * Exception in thread "main" java.lang.reflect.InaccessibleObjectException: Unable to make
   * public void jdk.internal.ref.Cleaner.clean() accessible: module java.base does not
   * "exports jdk.internal.ref" to unnamed module @6574b225
   * <p>
   * 解决方法
   * <p>https://zhuanlan.zhihu.com/p/66848304
   * <p>
   * jvm参数 --illegal-access=warn 减少打印报错日志
   */
  private static void invokeBufferClean(ByteBuffer directByteBuffer) throws Exception {
    // 类似 ((DirectBuffer) directByteBuffer).cleaner().clean(); 效果
    Field field = directByteBuffer.getClass().getDeclaredField("cleaner");
    field.setAccessible(true);
    Object cleaner = field.get(directByteBuffer);

    Method clean = cleaner.getClass().getDeclaredMethod("clean");
    clean.setAccessible(true);
    clean.invoke(cleaner);
  }
}
