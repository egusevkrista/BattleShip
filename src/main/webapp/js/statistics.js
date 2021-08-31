$(document).ready(function () {
    var ul = document.querySelector("ul#results");
    $.ajax({
        url: 'http://127.0.0.1:8080/BattleShip/api/winners/get',
        method: 'get',
        success: function (data) {
            data.forEach(function (winner, i, data) {
                var li = document.createElement('li');
                li.textContent = "Имя=" + winner.name + "; Результат=" + winner.result +
                    "; Начало игры=" + winner.startDate + "; Конец игры=" + winner.startDate;
                ul.appendChild(li);
            });
        },
    });

});