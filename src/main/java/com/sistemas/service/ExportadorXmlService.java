package com.sistemas.service;

import java.util.Map;

public interface ExportadorXmlService {
    Map<String, Object> exportarInventarioAXml();
    Map<String, Object> importarInventarioDesdeXml();
}