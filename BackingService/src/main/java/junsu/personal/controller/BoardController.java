package junsu.personal.controller;

import jakarta.validation.Valid;
import junsu.personal.dto.request.board.PostBoardRequestDTO;
import junsu.personal.dto.response.board.PostBoardRResponseDTO;
import junsu.personal.service.IBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {
    private final IBoardService boardService;

    @PostMapping("")
    public ResponseEntity<? super PostBoardRResponseDTO> postBoard(
            @RequestBody @Valid PostBoardRequestDTO requestBody,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PostBoardRResponseDTO> response = boardService.postBoard(requestBody, userId);
        return response;
    }

}
