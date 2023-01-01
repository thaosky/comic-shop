package com.example.demo.model.request.receipt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comic {
    private List<ComicDetail> comicDetailList;
    private Long comicId;
    private String comicName;
}
