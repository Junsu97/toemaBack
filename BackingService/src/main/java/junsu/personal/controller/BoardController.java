package junsu.personal.controller;

import com.amazonaws.Response;
import jakarta.validation.Valid;
import junsu.personal.dto.request.board.PatchBoardRequestDTO;
import junsu.personal.dto.request.board.PostBoardRequestDTO;
import junsu.personal.dto.request.board.PostCommentRequestDTO;
import junsu.personal.dto.response.board.*;
import junsu.personal.service.IBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final IBoardService boardService;

    @GetMapping("/{boardNumber}")
    public ResponseEntity<? super GetBoardResponseDTO> getBoard(@PathVariable("boardNumber") Long boardNumber) {
        log.info(this.getClass().getName() + ".getBoard Start!!!");
        ResponseEntity<? super GetBoardResponseDTO> response = boardService.getBoard(boardNumber);
        log.info(this.getClass().getName() + ".getBoard End!!!");
        return response;
    }

    @GetMapping("/{boardNumber}/favorite-list")
    public ResponseEntity<? super GetFavoriteListResponseDTO> getFavoriteList(@PathVariable("boardNumber") Long boardNumber) {
        log.info(this.getClass().getName() + ".getFavoriteList Start!!!");
        ResponseEntity<? super GetFavoriteListResponseDTO> response = boardService.getFavoriteList(boardNumber);
        log.info(this.getClass().getName() + ".getFavoriteList End!!!");
        return response;
    }

    @GetMapping("/{boardNumber}/comment-list")
    public ResponseEntity<? super GetCommentListResponseDTO> getCommentList(@PathVariable("boardNumber") Long boardNumber) {
        log.info(this.getClass().getName() + ".getCommentList Start!!!");
        ResponseEntity<? super GetCommentListResponseDTO> response = boardService.getCommentList(boardNumber);
        log.info(boardService.getCommentList(boardNumber).toString());
        log.info(this.getClass().getName() + ".getCommentList End!!!");
        return response;
    }

    @GetMapping("/{boardNumber}/increase-view-count")
    public ResponseEntity<? super IncreaseViewCountResponseDTO> increaseViewCount(@PathVariable("boardNumber") Long boardNumber) {
        log.info(this.getClass().getName() + ".increaseViewCount Start!!!");
        ResponseEntity<? super IncreaseViewCountResponseDTO> response = boardService.increaseViewCount(boardNumber);
        log.info(this.getClass().getName() + ".increaseViewCount End!!!");
        return response;

    }

    @PostMapping("write")
    public ResponseEntity<? super PostBoardRResponseDTO> postBoard(
            @RequestBody @Valid PostBoardRequestDTO requestBody,
            @AuthenticationPrincipal String userId
    ) {
        log.info(this.getClass().getName() + ".postBoard Start!!!");
        ResponseEntity<? super PostBoardRResponseDTO> response = boardService.postBoard(requestBody, userId);
        log.info(this.getClass().getName() + ".postBoard End!!!");
        return response;
    }

    @PostMapping("{boardNumber}/comment")
    public ResponseEntity<? super PostCommentResponseDTO> postComment(
            @RequestBody @Valid PostCommentRequestDTO requestBody,
            @PathVariable("boardNumber") Long boardNumber,
            @AuthenticationPrincipal String userId
    ) {
        log.info(this.getClass().getName() + ".postComment Start!!!");
        ResponseEntity<? super PostCommentResponseDTO> response = boardService.postComment(requestBody, boardNumber, userId);
        log.info(this.getClass().getName() + ".postComment End!!!");
        return response;
    }

    @PutMapping("/{boardNumber}/favorite")
    public ResponseEntity<? super PutFavoriteResponseDTO> putFavorite(
            @PathVariable("boardNumber") Long boardNumber,
            @AuthenticationPrincipal String userId
    ) {
        log.info(this.getClass().getName() + ".putFavorite Start!!!");
        ResponseEntity<? super PutFavoriteResponseDTO> response = boardService.putFavorite(boardNumber, userId);
        log.info(this.getClass().getName() + ".putFavorite End!!!");
        return response;
    }

    @PatchMapping("/{boardNumber}")
    public ResponseEntity<? super PatchBoardResponseDTO> patchBoard(
            @RequestBody @Valid PatchBoardRequestDTO pDTO,
            @PathVariable("boardNumber") Long boardNumber,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PatchBoardResponseDTO> response = boardService.patchBoard(pDTO, boardNumber, userId);
        return response;
    }

    @DeleteMapping("/{boardNumber}")
    public ResponseEntity<? super DeleteBoardResponseDTO> deleteBoard(
            @PathVariable("boardNumber") Long boardNumber,
            @AuthenticationPrincipal String userId
    ) {
        log.info(this.getClass().getName() + ".deleteBoard Start!!!");
        ResponseEntity<? super DeleteBoardResponseDTO> result = boardService.deleteBoard(boardNumber, userId);
        log.info(this.getClass().getName() + ".deleteBoard End!!!");
        return result;
    }
}
