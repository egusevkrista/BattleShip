$(document).ready(function () {
    let oneDeck;
    let twoDeck;
    let threeDeck;
    let fourDeck;
    $.ajax({
        url: 'http://127.0.0.1:8080/BattleShip/api/player/get',
        method: 'get',
        success: function (data) {
            $("div#name").text("Игрок: " + data);
        },
    });
    var tr, td, retrievedCoords, table = document.querySelector('table > tbody');

    for (var i = 0; i < 10; i++) {
        tr = document.createElement('tr');
        for (var j = 0; j < 10; j++) {
            td = document.createElement('td');
            td.style.backgroundColor = 'gray';
            td.dataset['cellCoords'] = JSON.stringify({i: i, j: j});
            tr.appendChild(td);
        }
        table.appendChild(tr);
    }

    $("table#userTable").click(function (e) {
        if (e.target.tagName !== 'TD') return false;
        retrievedCoords = JSON.parse(e.target.dataset['cellCoords']);
        console.log(retrievedCoords.i + " " + retrievedCoords.j);
    });
});