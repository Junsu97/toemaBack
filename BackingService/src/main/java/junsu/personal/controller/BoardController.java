package junsu.personal.controller;

import com.amazonaws.Response;
import jakarta.validation.Valid;
import junsu.personal.dto.request.board.PatchBoardRequestDTO;
import junsu.personal.dto.request.board.PostBoardRequestDTO;
import junsu.personal.dto.request.board.PostCommentRequestDTO;
import junsu.personal.dto.response.board.*;
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
    public ResponseEntity<? super GetFavoriteListResponseDTO> getFavoriteList(@PathVariable("boardNumber") Long boardNumber) {
        ResponseEntity<? super GetFavoriteListResponseDTO> response = boardService.getFavoriteList(boardNumber);
        return response;
    }

    @GetMapping("/{boardNumber}/comment-list")
    public ResponseEntity<? super GetCommentListResponseDTO> getCommentList(@PathVariable("boardNumber") Long boardNumber) {
        ResponseEntity<? super GetCommentListResponseDTO> response = boardService.getCommentList(boardNumber);
        return response;
    }

    @GetMapping("/{boardNumber}/increase-view-count")
    public ResponseEntity<? super IncreaseViewCountResponseDTO> increaseViewCount(@PathVariable("boardNumber") Long boardNumber) {
        ResponseEntity<? super IncreaseViewCountResponseDTO> response = boardService.increaseViewCount(boardNumber);
        return response;

    }

    @GetMapping("/latest-list")
    public ResponseEntity<? super GetLatestBoardListResponseDTO> getLatestBoardList(){
        ResponseEntity<? super GetLatestBoardListResponseDTO> response = boardService.getLatestBoardList();
        return response;
    }

    @GetMapping("/top-3")
    public ResponseEntity<? super GetTop3BoardListResponseDTO> getTop3BoardList(){
        ResponseEntity<? super GetTop3BoardListResponseDTO> response = boardService.getTop3BoardList();
        return response;
    }

    @GetMapping(value={"/search-list/{searchWord}","/search-list/{searchWord}/{preSearchWord}"})
    public ResponseEntity<? super GetSearchBoardListResponseDTO> getSearchBoardList(
            @PathVariable("searchWord") String searchWord,
            @PathVariable(value = "preSearchWord", required = false) String preSearchWord
    ){
        ResponseEntity<? super GetSearchBoardListResponseDTO> response = boardService.getSearchBoardList(searchWord,preSearchWord);
        return response;
    }

    @GetMapping("/user-board-list/{userId}")
    public ResponseEntity<? super GetUserBoardListResponseDTO> getUserBoardList(@PathVariable("userId") String userId){
        ResponseEntity<? super GetUserBoardListResponseDTO> response = boardService.getUserBoardList(userId);
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

    @PostMapping("{boardNumber}/comment")
    public ResponseEntity<? super PostCommentResponseDTO> postComment(
            @RequestBody @Valid PostCommentRequestDTO requestBody,
            @PathVariable("boardNumber") Long boardNumber,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PostCommentResponseDTO> response = boardService.postComment(requestBody, boardNumber, userId);
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
        ResponseEntity<? super DeleteBoardResponseDTO> result = boardService.deleteBoard(boardNumber, userId);
        return result;
    }
}
