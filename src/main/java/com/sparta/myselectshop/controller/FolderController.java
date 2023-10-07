package com.sparta.myselectshop.controller;

import com.sparta.myselectshop.dto.FolderRequestDto;
import com.sparta.myselectshop.security.UserDetailsImpl;
import com.sparta.myselectshop.service.FolderService;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class FolderController {
    FolderService folderService;
    @PostMapping("/folders")
    public void addFolders(@RequestBody FolderRequestDto folderRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<String> folderNames = folderRequestDto.getFolderNames();

        folderService.addFolders(folderNames, userDetails.getUser());
    }
}