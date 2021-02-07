package com.buer.job.utils;

import com.buer.job.exception.JobException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by jiewu on 2021/1/22
 */
@Slf4j
public class FileUtil {
  private static final double MAX_IMAGE_FILE_SIZE = 1000.0 * 1000 * 2;
  private static final double MAX_IMAGE_FILE_WIDTH = 4000;

  private static BufferedImage rotateImage(final BufferedImage bufferedimage,
                                           final int degree) {
    int iw = bufferedimage.getWidth();
    int ih = bufferedimage.getHeight();
    int w = iw;
    int h = ih;
    if (degree == 90 || degree == 270) {
      w = ih;
      h = iw;
    }

    int x = (w / 2) - (iw / 2);//确定原点坐标
    int y = (h / 2) - (ih / 2);


    int type = bufferedimage.getColorModel().getTransparency();
    BufferedImage img = new BufferedImage(w, h, type);
    Graphics2D graphics2d = img.createGraphics();
    graphics2d.setRenderingHint(
        RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    graphics2d.rotate(Math.toRadians(degree), w / 2.0, h / 2.0);//旋转图象
    graphics2d.translate(x, y);

    graphics2d.drawImage(bufferedimage, 0, 0, null);
    graphics2d.dispose();
    return img;
  }

  public static byte[] toByteArray(File file) throws IOException {
    return IOUtils.toByteArray(new FileInputStream(file));
  }

  public static byte[] resizeImage(File image) throws IOException {
    long start = Clock.now();
    try {
      byte[] imageByte = IOUtils.toByteArray(new FileInputStream(image));
      return resizeImage(imageByte);
    } finally {
      log.info("resize image cost {}", Clock.now() - start);
    }
  }

  public static byte[] resizeImage(byte[] data) throws IOException {
    double scale = getImageScale(data);
    if (scale < 1) {
      try (ByteArrayInputStream in = new ByteArrayInputStream(data);
           ByteArrayOutputStream out = new ByteArrayOutputStream()) {
        BufferedImage bufferedImage = scaleImage(in, scale);
        ImageIO.write(bufferedImage, "jpeg", out);
        data = out.toByteArray();
        try {
          bufferedImage.flush();
        } catch (Exception e) {
          log.warn("图片清理失败", e);
        }
      }
      return data;
    } else {
      return data;
    }
  }

  // 计算图片的压缩比例
  private static double getImageScale(byte[] data) {
    double scale = 1;
    try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data)) {
      BufferedImage bufferedImage = ImageIO.read(inputStream);
      if (bufferedImage == null)
        throw JobException.error("图片格式不正确");
      int width = bufferedImage.getWidth();
      int height = bufferedImage.getHeight();
      if (width <= MAX_IMAGE_FILE_WIDTH &&
          height <= MAX_IMAGE_FILE_WIDTH &&
          data.length <= MAX_IMAGE_FILE_SIZE) {
        return scale;
      }
      double widthScale = width > height ? MAX_IMAGE_FILE_WIDTH / width : MAX_IMAGE_FILE_WIDTH / height;
      double sizeScale = MAX_IMAGE_FILE_SIZE / data.length;
      double result = widthScale < sizeScale ? widthScale : sizeScale;
      try {
        bufferedImage.flush();
      } catch (Exception e) {
        log.warn("计算图片的压缩比例失败", e);
      }
      return result;
    } catch (IOException e) {
      log.warn("计算图片的压缩比例失败", e);
    }
    return scale;
  }

  private static BufferedImage scaleImage(ByteArrayInputStream in, double scale) {
    BufferedImage bufferedImage = null;
    Image image = null;
    try {
      bufferedImage = ImageIO.read(in);
      if (bufferedImage == null)
        throw JobException.error("图片格式不正确");
      int width = bufferedImage.getWidth();
      int height = bufferedImage.getHeight();

      width = (int) (width * scale);
      height = (int) (height * scale);

      image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      Graphics graphics = outputImage.getGraphics();
      graphics.drawImage(image, 0, 0, null);
      graphics.dispose();
      return outputImage;
    } catch (IOException e) {
      log.warn("照片缩放失败", e);
      throw JobException.error("照片缩放失败");
    } finally {
      try {
        if (image != null)
          image.flush();
        if (bufferedImage != null)
          bufferedImage.flush();
      } catch (Exception e) {
        log.warn("图片清理失败", e);
      }
    }
  }

  public byte[] rotateImage(byte[] byteImage, int degree) {
    if (degree != 0) {
      try (ByteArrayInputStream in = new ByteArrayInputStream(byteImage);
           ByteArrayOutputStream out = new ByteArrayOutputStream()) {
        BufferedImage image = ImageIO.read(in);
        if (image == null)
          throw JobException.error("非法图片类型");
        BufferedImage bufferedImage = rotateImage(image, degree);
        ImageIO.write(bufferedImage, "jpeg", out);
        byte[] result = out.toByteArray();
        try {
          image.flush();
          bufferedImage.flush();
        } catch (Exception e) {
          log.warn("图片清理失败", e);
        }
        return result;
      } catch (Exception ex) {
        throw JobException.wrap(ex);
      }
    }
    return byteImage;
  }
}
