package junsu.personal.service.impl;

import junsu.personal.dto.request.board.PostBoardRequestDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.board.PostBoardRResponseDTO;
import junsu.personal.entity.BoardEntity;
import junsu.personal.entity.ImageEntity;
import junsu.personal.repository.BoardRepository;
import junsu.personal.repository.ImageRepository;
import junsu.personal.repository.StudentUserRepository;
import junsu.personal.repository.TeacherUserRepository;
import junsu.personal.service.IBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService implements IBoardService {
    private final BoardRepository boardRepository;
    private final StudentUserRepository userRepository;
    private final ImageRepository imageRepository;

    @Override
    public ResponseEntity<? super PostBoardRResponseDTO> postBoard(PostBoardRequestDTO pDTO, String userId) {
        try{
            boolean existedUserId = userRepository.existsByUserId(userId);

            if(!existedUserId) return PostBoardRResponseDTO.notExistUser();
            BoardEntity boardEntity = new BoardEntity(pDTO, userId);
            boardRepository.save(boardEntity);

            long boardNumber = boardEntity.getBoardNumber();
            List<String> boardImageList = pDTO.boardImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            for(String image : boardImageList){
                ImageEntity imageEntity = ImageEntity.builder().
                        boardNumber(boardNumber)
                        .imageUrl(image)
                        .build();
                imageEntities.add(imageEntity);
            }
            imageRepository.saveAll(imageEntities);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return PostBoardRResponseDTO.success();
    }
}
