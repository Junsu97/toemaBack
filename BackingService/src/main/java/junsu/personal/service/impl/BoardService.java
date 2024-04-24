package junsu.personal.service.impl;

import junsu.personal.dto.request.board.PatchBoardRequestDTO;
import junsu.personal.dto.request.board.PostBoardRequestDTO;
import junsu.personal.dto.request.board.PostCommentRequestDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.board.*;
import junsu.personal.entity.BoardEntity;
import junsu.personal.entity.CommentEntity;
import junsu.personal.entity.FavoriteEntity;
import junsu.personal.entity.ImageEntity;
import junsu.personal.repository.*;
import junsu.personal.repository.resultSet.GetBoardResultSet;
import junsu.personal.repository.resultSet.GetCommentListResultSet;
import junsu.personal.repository.resultSet.GetFavoriteListResultSet;
import junsu.personal.service.IBoardService;
import junsu.personal.service.IFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService implements IBoardService {
    private final BoardRepository boardRepository;
    private final StudentUserRepository userRepository;
    private final TeacherUserRepository teacherUserRepository;
    private final ImageRepository imageRepository;
    private final FavoriteRepository favoriteRepository;
    private final CommentRepository commentRepository;
    private final IFileService fileService;

    @Override
    public ResponseEntity<? super GetBoardResponseDTO> getBoard(Long boardNumber) {

        GetBoardResultSet resultSet = null;
        List<ImageEntity> imageEntities = new ArrayList<>();
        try {
            resultSet = boardRepository.getBoard(boardNumber);
            if (resultSet == null) return GetBoardResponseDTO.noExistsBoard();

            imageEntities = imageRepository.findByBoardNumber(boardNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return GetBoardResponseDTO.success(resultSet, imageEntities);
    }

    @Override
    public ResponseEntity<? super GetFavoriteListResponseDTO> getFavoriteList(Long boardNumber) {
        List<GetFavoriteListResultSet> resultSets = new ArrayList<>();
        try {
            boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
            if (!existedBoard) return GetFavoriteListResponseDTO.noExistBoard();

            resultSets = favoriteRepository.getFavoriteList(boardNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return GetFavoriteListResponseDTO.success(resultSets);
    }

    @Override
    public ResponseEntity<? super GetCommentListResponseDTO> getCommentList(Long boardNumber) {
        List<GetCommentListResultSet> resultSets = new ArrayList<>();
        try {
            boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
            if (!existedBoard) return GetCommentListResponseDTO.noExistBoard();

            resultSets = commentRepository.getCommentList(boardNumber);
            for (int i = 0; i < resultSets.size(); i++) {
                log.info("리절트 셋 : " + resultSets.get(i).getNickname());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return GetCommentListResponseDTO.success(resultSets);
    }

    @Override
    public ResponseEntity<? super PostBoardRResponseDTO> postBoard(PostBoardRequestDTO pDTO, String userId) {
        try {
            boolean existedUserId = userRepository.existsByUserId(userId);

            if (!existedUserId) return PostBoardRResponseDTO.notExistUser();
            BoardEntity boardEntity = new BoardEntity(pDTO, userId);
            boardRepository.save(boardEntity);

            long boardNumber = boardEntity.getBoardNumber();
            List<String> boardImageList = pDTO.boardImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            for (String image : boardImageList) {
                ImageEntity imageEntity = ImageEntity.builder().
                        boardNumber(boardNumber)
                        .imageUrl(image)
                        .build();
                imageEntities.add(imageEntity);
            }
            log.info("images : " + imageEntities);
            imageRepository.saveAll(imageEntities);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return PostBoardRResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super PostCommentResponseDTO> postComment(PostCommentRequestDTO pDTO, Long boardNumber, String userId) {
        try {
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) return PostCommentResponseDTO.noExistBoard();
            boolean existedUser = userRepository.existsByUserId(userId) || teacherUserRepository.existsByUserId(userId);
            if (!existedUser) return PostCommentResponseDTO.noExistUser();

            CommentEntity commentEntity = new CommentEntity(pDTO, boardNumber, userId);
            commentRepository.save(commentEntity);

            boardEntity.increaseCommentCount();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return PostCommentResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super PutFavoriteResponseDTO> putFavorite(Long boardNumber, String userId) {
        try {
            boolean existedUser = userRepository.existsByUserId(userId) || teacherUserRepository.existsByUserId(userId);
            if (!existedUser) return PutFavoriteResponseDTO.noExistUser();

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) return PutFavoriteResponseDTO.noExistBoard();

            FavoriteEntity favoriteEntity = favoriteRepository.findByBoardNumberAndUserId(boardNumber, userId);
            if (favoriteEntity == null) {
                favoriteEntity = new FavoriteEntity(userId, boardNumber);
                favoriteRepository.save(favoriteEntity);
                boardEntity.increaseFavoriteCount();
            } else {
                favoriteRepository.delete(favoriteEntity);
                boardEntity.decreaseFavoriteCount();
            }

            boardRepository.save(boardEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return PutFavoriteResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super PatchBoardResponseDTO> patchBoard(PatchBoardRequestDTO pDTO, Long boardNumber, String userId) {
        try {
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if(boardEntity == null) return PatchBoardResponseDTO.noExistBoard();

            boolean existedUser = userRepository.existsByUserId(userId);
            if(!existedUser) return PatchBoardResponseDTO.noExistUser();

            String writeUserId = boardEntity.getWriterId();
            boolean isWriter = writeUserId.equals(userId);
            if(!isWriter) return PatchBoardResponseDTO.noPermission();

            boardEntity.patchBoard(pDTO);
            boardRepository.save(boardEntity);
            fileService.fileDelete(boardNumber);
            imageRepository.deleteByBoardNumber(boardNumber);
            List<String> boardImageList = pDTO.boardImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            for(String image : boardImageList){
                ImageEntity imageEntity = new ImageEntity(boardNumber, image);
                imageEntities.add(imageEntity);
            }
            imageRepository.saveAll(imageEntities);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return PatchBoardResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super IncreaseViewCountResponseDTO> increaseViewCount(Long boardNumber) {
        try {
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) return IncreaseViewCountResponseDTO.noExistsBoard();
            boardEntity.increaseViewCount();
            boardRepository.save(boardEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }

        return IncreaseViewCountResponseDTO.success();
    }

    @Override
    public ResponseEntity<? super DeleteBoardResponseDTO> deleteBoard(Long boardNumber, String userId) {
        try {
            boolean existedUser = userRepository.existsByUserId(userId);
            if (!existedUser) return DeleteBoardResponseDTO.noExistUser();

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) return DeleteBoardResponseDTO.noExistBoard();

            String writerId = boardEntity.getWriterId();
            boolean isWriter = writerId.equals(userId);
            if (!isWriter) return DeleteBoardResponseDTO.noPermission();
            fileService.fileDelete(boardNumber);
            imageRepository.deleteByBoardNumber(boardNumber);
            commentRepository.deleteByBoardNumber(boardNumber);
            favoriteRepository.deleteByBoardNumber(boardNumber);

            boardRepository.delete(boardEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return DeleteBoardResponseDTO.success();
    }
}
