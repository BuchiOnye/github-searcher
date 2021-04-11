const resultSection = document.getElementById('result')
const emptyState = document.getElementById('emptyState')
const analyticsSection = document.getElementById('analytics')

var resultsArray = [
    {
        name: 'denver flows',
        date: '12/1/2010'
    },
    {
        name: 'denver flows 2',
        date: '12/1/2019'
    }
]

var data = {
    labels: ["11/21/2341", "12/12/2019", "Mar", "Apr", "May", "Jun", "Jul"],
    datasets: [{
        label: "Commit Impact",
        backgroundColor: "rgba(255,99,132,0.2)",
        borderColor: "rgba(255,99,132,1)",
        borderWidth: 2,
        hoverBackgroundColor: "rgba(255,99,132,0.4)",
        hoverBorderColor: "rgba(255,99,132,1)",
        data: [65, 59, 20, 81, 56, 55, 40],
    }]
};

var options = {
    maintainAspectRatio: false,
    scales: {
        yAxes: [{
            stacked: true,
            gridLines: {
                display: true,
                color: "rgba(255,99,132,0.2)"
            }
        }],
        xAxes: [{
            gridLines: {
                display: false
            }
        }]
    }
};

Chart.Bar('commitImpact', {
    options: options,
    data: data
});

const onSearch = (e) => {
    e.preventDefault();
    document.getElementById("resultDiv").innerHTML = '';

    emptyState.classList.add('d-none');
    emptyState.classList.remove('d-block');

    resultSection.classList.add('d-block');
    resultSection.classList.remove('d-none');

var searchBox = document.getElementById("searchBox").value;
$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "user/repository",
		data : { 
    	search : searchBox
  		},
		dataType : 'json',				
		success : function(data) {
		if(data.items.length < 1){
			emptyState.classList.remove('d-none');
    		emptyState.classList.add('d-block');
    		resultSection.classList.remove('d-block');
   			resultSection.classList.add('d-none');
   			return;
		}
		
		var searchResultParent = '<div>';
		
		 data.items.forEach(function (result) {
        // searchResultParent += '<div class="col-md-12">' + slide + '</div>';

	

        searchResultParent +=
            '<div class="col-md-12 card p-4 mb-4">' +
            '<div class="row">' +
            '<div class="col-md-8 my-auto">' +
            '<h6>' + result.name +'</h6>' +
            '<p class="mb-0 text-secondary">' + new Date(result.created_at).toLocaleDateString() + '</p>' +
            '</div>' +

            '<div class="col-md-4 my-auto">' +
            '<button class="btn btn-primary px-4 py-2" style="font-size: 14px;">View Contributions</button>' +
            '</div>' +
            '</div>' +
            ' </div>';
    });

    searchResultParent += '</div>';
    document.getElementById("resultDiv").innerHTML = '<div class="flows">' + searchResultParent + '</div>';

		}
	});

   
}