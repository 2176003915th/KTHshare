package idusw.leafton.model.DTO;


import idusw.leafton.model.entity.Member;
import idusw.leafton.model.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RoomDTO {
    private Long roomId;
    private MemberDTO memberDTO;
    private String unityData;
    public static RoomDTO toRoomDTO(Room room){
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomId(room.getRoomId());
        roomDTO.setMemberDTO(MemberDTO.toMemberDTO(room.getMember()));
        roomDTO.setUnityData(room.getUnityData());
        return roomDTO;
    }
}
