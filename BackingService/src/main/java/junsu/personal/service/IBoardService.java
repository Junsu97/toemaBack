package junsu.personal.service;

import junsu.personal.dto.request.board.PostBoardRequestDTO;
import junsu.personal.dto.request.board.PostCommentRequestDTO;
import junsu.personal.dto.response.board.*;
import org.springframework.http.ResponseEntity;

public interface IBoardService {
    ResponseEntity<? super GetBoardResponseDTO> getBoard(Long boardNumber);
    ResponseEntity<? super GetFavoriteListResponseDTO> getFavoriteList(Long boardNumber);
    ResponseEntity<? super PostBoardRResponseDTO> postBoard(PostBoardRequestDTO pDTO, String userId);
    ResponseEntity<? super PostCommentResponseDTO> postComment(PostCommentRequestDTO pDTO, Long boardNumber,String userId);

    ResponseEntity<? super PutFavoriteResponseDTO> putFavorite(Long boardNumber, String userId);

}
