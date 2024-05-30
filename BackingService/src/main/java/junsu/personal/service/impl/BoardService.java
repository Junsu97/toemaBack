package junsu.personal.service.impl;

import junsu.personal.dto.request.board.PatchBoardRequestDTO;
import junsu.personal.dto.request.board.PatchCommentRequestDTO;
import junsu.personal.dto.request.board.PostBoardRequestDTO;
import junsu.personal.dto.request.board.PostCommentRequestDTO;
import junsu.personal.dto.response.ResponseDTO;
import junsu.personal.dto.response.board.*;
import junsu.personal.entity.*;
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

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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
    private final SearchLogRepository searchLogRepository;
    private final BoardListViewRepository boardListViewRepository;
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
    public ResponseEntity<? super GetUserBoardListResponseDTO> getUserBoardList(String userId) {
        List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();
        try{
            boolean existedUser = userRepository.existsByUserId(userId);
            if(!existedUser) return GetUserBoardListResponseDTO.noExistUser();

            boardListViewEntities = boardListViewRepository.findByWriterIdOrderByWriteDatetimeDesc(userId);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return GetUserBoardListResponseDTO.success(boardListViewEntities);
    }

    @Override
    public ResponseEntity<? super GetLatestBoardListResponseDTO> getLatestBoardList() {
        List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();
        try{
            boardListViewEntities = boardListViewRepository.findByOrderByWriteDatetimeDesc();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return GetLatestBoardListResponseDTO.success(boardListViewEntities);
    }

    @Override
    public ResponseEntity<? super GetSearchBoardListResponseDTO> getSearchBoardList(String searchWord, String preSearchWord) {
        List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();
        try{
            boardListViewEntities = boardListViewRepository.findByTitleContainsOrContentContainsOrderByWriteDatetimeDesc(searchWord, searchWord);

            SearchLogEntity searchLogEntity = new SearchLogEntity(searchWord, preSearchWord, false);
            searchLogRepository.save(searchLogEntity);

            boolean relation = preSearchWord != null;
            if(relation){
                log.info("트루에용");
                searchLogEntity = new SearchLogEntity(preSearchWord, searchWord, true);
                searchLogRepository.save(searchLogEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
            ResponseDTO.databaseError();
        }
        return GetSearchBoardListResponseDTO.success(boardListViewEntities);
    }

    @Override
    public ResponseEntity<? super GetTop3BoardListResponseDTO> getTop3BoardList() {
        log.info("getTop3 Start!!!");
        List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();
        try{
            Date beforeWeek = Date.from(Instant.now().minus(7, ChronoUnit.DAYS));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sevenDaysAgo = simpleDateFormat.format(beforeWeek);
            boardListViewEntities = boardListViewRepository.findTop3ByWriteDatetimeGreaterThanOrderByFavoriteCountDescCommentCountDescViewCountDescWriteDatetimeDesc(sevenDaysAgo);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return GetTop3BoardListResponseDTO.success(boardListViewEntities);
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
            long seq = commentRepository.countByBoardNumber(boardNumber) + 1;
            CommentEntity commentEntity = new CommentEntity(seq,pDTO, boardNumber, userId);
            commentRepository.save(commentEntity);

            boardEntity.increaseCommentCount();
            boardRepository.save(boardEntity);
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
    public ResponseEntity<? super PatchCommentResponseDTO> patchComment(PatchCommentRequestDTO requestBody, String userId) {
        try{
            log.info("commentNumbr : " + requestBody.commentNumber());
            log.info("boardNumber: " + requestBody.boardNumber());
            CommentEntity entity = commentRepository.findByBoardNumberAndCommentNumber(requestBody.boardNumber(),requestBody.commentNumber());
            if(entity == null){
                return PatchCommentResponseDTO.noExistComment();
            }
            if(!entity.getUserId().equals(userId)) return PatchCommentResponseDTO.noPermission();
            entity.patchComment(requestBody);
            commentRepository.save(entity);


        }catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.databaseError();
        }
        return PatchCommentResponseDTO.success();
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

    @Override
    public ResponseEntity<? super DeleteCommentResponseDTO> deleteComment(Long boardNumber, Long commentNumber, String userId) {
        try{
            CommentEntity entity = commentRepository.findByBoardNumberAndCommentNumber(boardNumber,commentNumber);

            if(entity == null){
                return DeleteCommentResponseDTO.noExistComment();
            }
            commentRepository.delete(entity);
        }catch (Exception e){
            e.printStackTrace();
            ResponseDTO.databaseError();
        }
        return DeleteCommentResponseDTO.success();
    }
}
