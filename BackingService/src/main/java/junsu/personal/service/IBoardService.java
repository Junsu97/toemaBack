package junsu.personal.service;

import junsu.personal.dto.request.board.PostBoardRequestDTO;
import junsu.personal.dto.response.board.GetBoardResponseDTO;
import junsu.personal.dto.response.board.GetFavoriteListResponseDTO;
import junsu.personal.dto.response.board.PostBoardRResponseDTO;
import junsu.personal.dto.response.board.PutFavoriteResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IBoardService {
    ResponseEntity<? super GetBoardResponseDTO> getBoard(Long boardNumber);
    ResponseEntity<? super GetFavoriteListResponseDTO> getFavoriteList(Long boardNumber);
    ResponseEntity<? super PostBoardRResponseDTO> postBoard(PostBoardRequestDTO pDTO, String userId);

    ResponseEntity<? super PutFavoriteResponseDTO> putFavorite(Long boardNumber, String userId);

}
