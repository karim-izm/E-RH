package com.example.application.views.Employe;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.example.application.models.Employee;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.ByteArrayOutputStream;

public class PdfGenerator {

    public static byte[] generateEmployeeInfoPDF(Employee employee) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
            Document document = new Document(pdfDocument);

            // Title
            Paragraph title = new Paragraph("Fiche d'Employé")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20);
            document.add(title);

            // Employee Information
            Paragraph employeeInfo = new Paragraph()
                    .add("Nom complet: " + employee.getFullName())
                    .add("\nCIN: " + employee.getCin())
                    .add("\nGenre: " + employee.getGender())
                    .add("\nDate de Naissance: " + employee.getBirthDate())
                    .add("\nEmail: " + employee.getEmail())
                    .add("\nTéléphone: " + employee.getTele())
                    .add("\nDate d'embauche: " + employee.getDateEmbauche())
                    .add("\nDébut de Contrat: " + employee.getDateDebutCt())
                    .add("\nFin de Contrat: " + employee.getDateFinCt())
                    .add("\nDépartement: " + employee.getDepartement())
                    .add("\nAdresse: " + employee.getAdresse())
                    .add("\nSituation familiale: " + employee.getSituation_familiale())
                    .add("\nNombre d'enfants: " + employee.getNb_enfants())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontSize(12);
            document.add(employeeInfo);

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
