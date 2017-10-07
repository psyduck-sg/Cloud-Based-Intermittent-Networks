const WebSocket = require('ws');
const fs = require('fs');
const ws = new WebSocket('ws://127.0.0.1:8086/'); // ws://172.31.9.255:80/

ws.on('open', function open(){
	console.log('connected');
	var fileName = "./clientFolder/userData.json";

			fs.exists(fileName, function(exists) {
			  if (exists) {
			    fs.stat(fileName, function(error, stats) {
			      fs.open(fileName, "r", function(error, fd) {
			        var buffer = new Buffer(stats.size);
			        console.log(Object.prototype.toString.apply(buffer));

			        fs.read(fd, buffer, 0, buffer.length, null, function(error, bytesRead, buffer) {
			          //var data = buffer.toString("utf8", 0, buffer.length);
			           ws.send(buffer);
			          //console.log(data);
			          fs.close(fd);
			        });
			      });
			    });
			  }
			});
});
ws.on('close', function close(){
	console.log('disconnected');
});

ws.on('message', function incoming(data, flags){
	if(data==200){
		ws.send(100);
	}
	else if(data==100){
		console.log("ok done");
	}
	else{
			console.log(data);
			//var data1 = data.toString("utf8", 0, data.length);
			fs.writeFile('./clientFolder/bundle.zip',data, function(err){
				if(err){
					 console.log(err);
					 return;
				}
				// otherwise file got saved
				console.log("file got saved");

			});


	}

});
