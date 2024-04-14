package junsu.personal.service;

import junsu.personal.dto.request.board.PostBoardRequestDTO;
import junsu.personal.dto.response.board.PostBoardRResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IBoardService {
    ResponseEntity<? super PostBoardRResponseDTO> postBoard(PostBoardRequestDTO pDTO, String userId);

}
