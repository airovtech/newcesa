function getByteLength(value) {
	var length = 0;

	for(var i = 0; i < value.length; i++){
		if(escape(value.charAt(i)).length >= 4)
			length += 2;
		else if(escape(value.charAt(i)) != "%0D")
			length++;
	}

	return length;
}