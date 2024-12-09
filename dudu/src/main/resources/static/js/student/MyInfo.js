$(document).ready(function () {
    // 팝업 HTML 로드
    $('#popupContainer').load('InfoPopup.html', function () {
        const modal = $('#reservationModal');
        const modalContent = $('#reservationContainer');

        // 예약 버튼 클릭 시
        $('#loadReservationBtn').click(function () {
            const studentId = $('#studentId').text().trim();

            // 예약 정보를 가져오는 AJAX 요청
            $.ajax({
                url: '/search/reservation', // 예약 정보 조회 API
                method: 'GET',
                data: { 'student-id': studentId },
                success: function (data) {
                    let reservationsHTML = '';

                    if (data.content.length === 0) {
                        reservationsHTML = '<p>예약 내역이 없습니다.</p>';
                    } else {
                        data.content.forEach(function (reservation) {
                            reservationsHTML += `
                                <p>예약 시작 시간: ${new Date(reservation.reservationStartTime).toLocaleString()}</p>
                                <p>예약 종료 시간: ${new Date(reservation.reservationEndTime).toLocaleString()}</p>
                                <p>예약 타입: ${reservation.reservationType}</p>
                                <hr />
                            `;
                        });
                    }

                    modalContent.html(reservationsHTML); // 예약 정보 업데이트
                    modal.show(); // 모달 창 열기
                },
                error: function () {
                    alert('예약 정보를 불러오는 데 실패했습니다.');
                }
            });
        });

        // 닫기 버튼 클릭 시
        $(document).on('click', '.close-btn', function () {
            modal.hide(); // 모달 창 닫기
        });

        // 모달 창 외부 클릭 시 닫기
        $(window).click(function (event) {
            if ($(event.target).is(modal)) {
                modal.hide();
            }
        });
    });
});
