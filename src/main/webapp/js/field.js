$(document).ready(function () {
    let sizearr = [5];
    var tr, td, table = document.querySelector('table > tbody');

    $.ajax({
        url: 'http://127.0.0.1:8080/BattleShip/api/player/get',
        method: 'get',
        success: function (data) {
            $("div#name").text("Игрок: " + data);
        },
    });

    $.ajax({
        url: 'http://127.0.0.1:8080/BattleShip/api/ships/get',
        method: 'get',
        success: function (data) {
            data.forEach(function (ship, i, data) {
                paint(ship);
            });
        },
    });

    for (var i = 0; i < 10; i++) {
        tr = document.createElement('tr');
        for (var j = 0; j < 10; j++) {
            td = document.createElement('td');
            td.style.backgroundColor = 'gray';
            td.dataset['cellCoords'] = `${i + 1}:${j + 1}`;
            tr.appendChild(td);
        }
        table.appendChild(tr);
    }

    document.oncontextmenu = function () {
        return false;
    };

    function paint(ship) {
        switch (ship.direction) {
            case "LEFT": {
                for (var i = ship.y; i > ship.y - ship.size; i--) {
                    td = document.querySelector(`td[data-cell-coords="${ship.x}:${i}"]`);
                    td.style.backgroundColor = "red";
                }
                break;
            }
            case "DOWN": {
                for (var i = ship.x; i < ship.x + ship.size; i++) {
                    td = document.querySelector(`td[data-cell-coords="${i}:${ship.y}"]`);
                    td.style.backgroundColor = "red";
                }
                break;
            }
        }
    }

    function parseX(str) {
        var arr = str.split(':');
        return parseInt(arr[0]);
    }

    function parseY(str) {
        var arr = str.split(':');
        return parseInt(arr[1]);
    }

    $("table#userTable").mousedown(function (e) {
        if (e.target.tagName !== 'TD') return false;
        switch (e.which) {
            case 1: {
                var ship = JSON.stringify({
                    x: parseX(e.target.dataset['cellCoords']),
                    y: parseY(e.target.dataset['cellCoords']),
                    size: parseInt($("input[name=sizeradio]:checked").val()),
                    direction: "LEFT"
                });
                $.ajax({
                    url: 'http://127.0.0.1:8080/BattleShip/api/ships/set',
                    method: 'put',
                    contentType: "application/json",
                    dataType: "json",
                    data: ship,
                    success: function () {
                        paint(JSON.parse(ship));
                        sizearr[ship.size]++;
                    }
                });
                break;
            }
            case 3: {
                var ship = JSON.stringify({
                    x: parseX(e.target.dataset['cellCoords']),
                    y: parseY(e.target.dataset['cellCoords']),
                    size: parseInt($("input[name=sizeradio]:checked").val()),
                    direction: "DOWN"
                });
                $.ajax({
                    url: 'http://127.0.0.1:8080/BattleShip/api/ships/set',
                    method: 'put',
                    contentType: "application/json",
                    dataType: "json",
                    data: ship,
                    success: function () {
                        paint(JSON.parse(ship));
                        sizearr[ship.size]++;
                    }
                });
                break;
            }
        }
    });
});