package com.example.tryvault_ti.service;

import com.example.tryvault_ti.model.FundLoad;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface FundLoadService {

    File processFundLoadData(InputStream fundLoadData);
}
