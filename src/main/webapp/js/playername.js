$(document).ready(function(){
    $("#btnSubmit").click(function(){
        var playerName = document.getElementById("name").value;
        $.ajax({
            url: 'http://127.0.0.1:8080/BattleShip/api/player/set?name=' + playerName,
            method: 'post',
            success: function (msg) {
                alert(msg);
              },
        }); 
        $.ajax({
            url: 'http://127.0.0.1:8080/BattleShip/api/player/get',
            method: 'get',
            success: function(data){
                alert("Player getted:" + data);     
            }
        });      
    });
  });