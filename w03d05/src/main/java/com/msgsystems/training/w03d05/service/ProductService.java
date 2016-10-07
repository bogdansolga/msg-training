package com.msgsystems.training.w03d05.service;

import com.msgsystems.training.w03d05.repository.ProductRepository;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public int saveImage(final String imageName) {
        final int savedId = productRepository.saveBLOB(imageName);
        System.out.println("The image '" + imageName + "' was successfully saved, it has the ID " + savedId);

        return savedId;
    }

    public int saveDescription(final String descriptionFileName) {
        final int savedId = productRepository.saveCLOB(descriptionFileName);
        System.out.println("The description file '" + descriptionFileName + "' was successfully saved, it has the ID " + savedId);

        return savedId;
    }

    public void loadImage(final int id, final String savedFileName) {
        int character;
        try (final InputStream inputStream = productRepository.loadSavedImage(id);
             final OutputStream outputStream = new FileOutputStream(savedFileName)) {

            while ((character = inputStream.read()) > -1) {
                outputStream.write(character);
            }
            outputStream.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        System.out.println("The image with the ID '" + id + "' was successfully saved as the file '" + savedFileName + "'");
    }

    public void loadDescription(final int id, final String savedFileName) {
        int character;

        try (final Reader reader = productRepository.loadSavedDescription(id);
             final Writer writer = new FileWriter(savedFileName)) {

            while ((character = reader.read()) > -1) {
                writer.write(character);
            }
            writer.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        System.out.println("The description with the ID '" + id + "' was successfully saved as the file '" + savedFileName + "'");
    }
}

