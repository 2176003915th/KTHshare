package idusw.leafton.model.entity;


import idusw.leafton.model.DTO.RoomDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="Room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomId")
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(columnDefinition = "LONGTEXT")
    private String unityData;

    public static Room toRoomEntity(RoomDTO roomDTO){
        Room room = new Room();
        room.setRoomId(roomDTO.getRoomId());
        room.setMember(Member.toMemberEntity(roomDTO.getMemberDTO()));
        room.setUnityData(roomDTO.getUnityData());

        return room;
    }
}
