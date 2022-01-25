package com.dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "GPU_information")
public class Gpuinfor {


            private String gpu_type;
            private String cuda_core;
            private String gpu_mem;
            private String calc_point;
}
