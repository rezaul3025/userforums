function readURL(input)
{
    alert("Test"+input);
    
    if (input.files)
    {
    	var selectedFiles = input.files;
    	
    	for(var i=0;i<selectedFiles.length;i++)
    	{
        
	    	var reader = new FileReader();
	
	        reader.onload = function(e)
	        {
	        	$('#selectedImageContainer').append('<img src="'+e.target.result+'" width="100" height="100" /><hr/>');
	            
	           //$('#logoPreview').attr('src', e.target.result); 
	            
	        }
	
	        reader.readAsDataURL(input.files[i]);
    	}
    }
}

//using FormData() object
function uploadFormData(){
   // $('#result').html('');
 
  var oMyForm = new FormData();
 // alert("Alert"+oMyForm);
  //oMyForm.append("files",  $( '#files' )[0].files);
  
  for (var i = 0, len = document.getElementById('files').files.length; i < len; i++) {
	  oMyForm.append("files", document.getElementById('files').files[i]);
  }
 
  $.ajax({
    url: '/userforums/add_uploadable_image',
    data: oMyForm,
    dataType: 'text',
    processData: false,
    contentType: false,
    type: 'POST',
    success: function(data){
     // $('#result').html(data);
    	$('#selectedImageContainer').html('' );
    	$('#selectedImageContainer').append(data);
    }
  });
}


//using FormData() object
function deleteUploadableImage(imageName){
 
  $.ajax({
    url: '/userforums/delete_uploadable_image/'+imageName,
    //data: oMyForm,
    dataType: 'text',
    processData: false,
    contentType: false,
    type: 'GET',
    success: function(data){
     // $('#result').html(data);
    	$('#selectedImageContainer').html('' );
    	$('#selectedImageContainer').append(data);
    }
  });
}

