$(document).ready(function () {
    $("#btnSubmit").click(function () {
        let playerName = document.getElementById("name").value;
        $.ajax({
            url: 'http://127.0.0.1:8080/BattleShip/api/player/set?name=' + playerName,
            method: 'post',
            success: function () {
                window.location.replace("http://127.0.0.1:8080/BattleShip/field.html");
            },
        });
    });
});