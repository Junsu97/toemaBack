package junsu.personal.controller;

import com.amazonaws.Response;
import jakarta.validation.Valid;
import junsu.personal.dto.request.board.PostBoardRequestDTO;
import junsu.personal.dto.response.board.GetBoardResponseDTO;
import junsu.personal.dto.response.board.GetFavoriteListResponseDTO;
import junsu.personal.dto.response.board.PostBoardRResponseDTO;
import junsu.personal.dto.response.board.PutFavoriteResponseDTO;
import junsu.personal.service.IBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {
    private final IBoardService boardService;

    @GetMapping("/{boardNumber}")
    public ResponseEntity<? super GetBoardResponseDTO> getBoard(@PathVariable("boardNumber") Long boardNumber) {
        ResponseEntity<? super GetBoardResponseDTO> response = boardService.getBoard(boardNumber);
        return response;
    }

    @GetMapping("/{boardNumber}/favorite-list")
    public ResponseEntity<? super GetFavoriteListResponseDTO> getFavoriteList(@PathVariable("boardNumber") Long boardNumber){
        ResponseEntity<? super GetFavoriteListResponseDTO> response = boardService.getFavoriteList(boardNumber);
        return response;
    }

    @PostMapping("write")
    public ResponseEntity<? super PostBoardRResponseDTO> postBoard(
            @RequestBody @Valid PostBoardRequestDTO requestBody,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PostBoardRResponseDTO> response = boardService.postBoard(requestBody, userId);
        return response;
    }

    @PutMapping("/{boardNumber}/favorite")
    public ResponseEntity<? super PutFavoriteResponseDTO> putFavorite(
            @PathVariable("boardNumber") Long boardNumber,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PutFavoriteResponseDTO> response = boardService.putFavorite(boardNumber, userId);
        return response;
    }

}
