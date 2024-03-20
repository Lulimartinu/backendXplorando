package com.dh.Xplorando.controller;

import com.dh.Xplorando.dto.entrada.ImagenEntradaDto;
import com.dh.Xplorando.dto.entrada.ProductoEntradaDto;
import com.dh.Xplorando.dto.salida.ImagenSalidaDto;
import com.dh.Xplorando.dto.salida.ProductoSalidaDto;
import com.dh.Xplorando.service.IImagenService;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/imagenes")
@CrossOrigin
public class ImagenController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagenController.class);
    private final IImagenService imagenService;

    public ImagenController(IImagenService imagenService) {
        this.imagenService = imagenService;
    }


    @PostMapping("/crear")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ImagenSalidaDto> crearImagen(@RequestBody @Valid ImagenEntradaDto imagenEntradaDto) {
        return new ResponseEntity<>(imagenService.crearImagen(imagenEntradaDto), HttpStatus.CREATED);
    }

    public static String UPLOAD_DIRECTORY = "/uploads";

    /*  @GetMapping("/uploadimage") public String displayUploadForm() {
            return "imageupload/index";
        }

        @PostMapping("/upload") public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
            LOGGER.info("Subiendo imagen ... ");
            LOGGER.info("Subiendo imagen ... ");
            StringBuilder fileNames = new StringBuilder();
            java.nio.file.Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
            model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
            return "imageupload/index";
        }

*/
}


