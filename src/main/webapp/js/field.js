$(document).ready(function () {
    let countShips = 0;
    var tr, td, table = document.querySelector('table#userTable>tbody');

    var optr, optd, optable = document.querySelector('table#opTable>tbody');
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

    for (var i = 0; i < 10; i++) {
        optr = document.createElement('tr');
        for (var j = 0; j < 10; j++) {
            optd = document.createElement('td');
            optd.style.backgroundColor = 'gray';
            optd.dataset['opCoords'] = `${i + 1}:${j + 1}`;
            optr.appendChild(optd);
        }
        optable.appendChild(optr);
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

    function fullPlacement() {
        if (countShips == 10) {
            alert("Корабли расставлены, игра начинается.")
            $("#sizeradiobtns").hide();
            $("div#opName").hidden
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

    function paintBotShot(x, y, hit) {
        td = document.querySelector(`td[data-cell-coords="${x}:${y}"]`);
        if (hit == false) {
            td.style.backgroundColor = "black";
        } else {
            td.style.backgroundColor = "green";
        }
    }

    $("table#userTable").mousedown(function (e) {
        if (e.target.tagName !== 'TD') return false;
        var direction = "";
        if (e.which == 1) {
            direction = "LEFT";
        } else if (e.which == 3) {
            direction = "DOWN";
        }
        var ship = JSON.stringify({
            x: parseX(e.target.dataset['cellCoords']),
            y: parseY(e.target.dataset['cellCoords']),
            size: parseInt($("input[name=sizeradio]:checked").val()),
            direction: direction
        });
        $.ajax({
            url: 'http://127.0.0.1:8080/BattleShip/api/ships/set',
            method: 'put',
            contentType: "application/json",
            dataType: "json",
            data: ship,
            success: function () {
                paint(JSON.parse(ship));
                countShips++;
                fullPlacement()
            }
        });
    });

    $("table#opTable").mousedown(function (e) {
        if (e.target.tagName !== 'TD') return false;
        var x = parseX(e.target.dataset['opCoords']);
        var y = parseY(e.target.dataset['opCoords']);
        optd = document.querySelector(`td[data-op-coords="${x}:${y}"]`);
        $.ajax({
            url: 'http://127.0.0.1:8080/BattleShip/api/ships/fire?x=' + x + '&y=' + y,
            method: 'put',
            success: function (data) {
                switch (data) {
                    case 1: {
                        optd.style.backgroundColor = "red";
                        break;
                    }
                    case 2: {
                        optd.style.backgroundColor = "black";
                        break;
                    }
                    case 0: {
                        alert("Вы победили!");
                        break;
                    }
                    case 3: {
                        alert("Вы проиграли!");
                        break;
                    }

                }
            }
        });

        $.ajax({
            url: 'http://127.0.0.1:8080/BattleShip/api/ships/shots/get',
            method: 'get',
            contentType: "application/json",
            dataType: "json",
            success: function (data) {
                data.forEach(function (shot, i, data) {
                    paintBotShot(shot.x, shot.y, shot.hit);
                });
            }
        });
    });
});