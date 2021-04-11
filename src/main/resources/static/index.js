const resultSection = document.getElementById('result')
const emptyState = document.getElementById('emptyState')
const analyticsSection = document.getElementById('analytics')
const searchDiv = document.getElementById('search_div')


var resultsArray = [{
        name: 'denver flows',
        date: '12/1/2010'
    },
    {
        name: 'denver flows 2',
        date: '12/1/2019'
    }
]

let contributorsArray = [{
        'S/N': '1',
        avartar: '/images/default-img.jpeg',
        name: 'SFXbuchiflows',
        contributions: 'flow.buchi@yopail.com',
        type: '11/21/1221',
        siteAdmin: '102'
    },
    {
        'S/N': '2',
        avartar: '/images/default-img.jpeg',
        name: 'SFXbuchiflows',
        contributions: 'flow.buchi@yopail.com',
        type: '11/21/1221',
        siteAdmin: '102'
    },
]



const onSearch = (e) => {
    e.preventDefault();
    document.getElementById("resultDiv").innerHTML = '';

    emptyState.classList.add('d-none');
    emptyState.classList.remove('d-block');

    resultSection.classList.add('d-block');
    resultSection.classList.remove('d-none');

    var searchBox = document.getElementById("searchBox").value;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "user/repository",
        data: {
            search: searchBox
        },
        dataType: 'json',
        success: function(data) {
            if (data.items.length < 1) {
                emptyState.classList.remove('d-none');
                emptyState.classList.add('d-block');
                resultSection.classList.remove('d-block');
                resultSection.classList.add('d-none');
                return;
            }

            var searchResultParent = '<div>';

            data.items.forEach(function(result) {
                // searchResultParent += '<div class="col-md-12">' + slide + '</div>';



                searchResultParent +=
                    '<div class="col-md-12 card p-4 mb-4">' +
                    '<div class="row">' +
                    '<div class="col-md-8 my-auto">' +
                    '<h6>' + result.name + '</h6>' +
                    '<p class="mb-0 text-secondary">' + new Date(result.created_at).toLocaleDateString() + '</p>' +
                    '</div>' +

                    '<div class="col-md-4 my-auto">' +
                    '<button onclick="proceedToAnalytics(\'' + result.name + '\');" class="btn btn-primary px-4 py-2" style="font-size: 14px;">View Contributions</button>' +
                    '</div>' +
                    '</div>' +
                    ' </div>';
            });

            searchResultParent += '</div>';
            document.getElementById("resultDiv").innerHTML = '<div class="flows">' + searchResultParent + '</div>';

        }
    });
}


const proceedToAnalytics = (result) => {
    emptyState.classList.add('d-none')
    emptyState.classList.remove('d-block')

    resultSection.classList.remove('d-block')
    resultSection.classList.add('d-none')

    searchDiv.classList.add('d-none')
    searchDiv.classList.remove('d-block')


    analyticsSection.classList.add('d-block')
    analyticsSection.classList.remove('d-none')
    document.getElementById("repo_name").innerHTML = result;
    generateCommitImpactGraph(result);
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "user/contributor",
        data: {
            repo: result
        },
        dataType: 'json',
        success: function(data) {
            if (data.length < 1) {
                emptyState.classList.remove('d-none');
                emptyState.classList.add('d-block');
                resultSection.classList.remove('d-block');
                resultSection.classList.add('d-none');
                return;
            }

            let table = document.querySelector("table");
            let tableData = Object.keys(contributorsArray[0]);
            generateTableHead(table, tableData);
            generateTable(table, data);

        }
    });

}

function generateTableHead(table, data) {
    while (table.rows.length > 0) {
        table.deleteRow(0);
    }
    let thead = table.createTHead();
    let row = thead.insertRow();
    for (let key of data) {
        let th = document.createElement("th");
        let text = document.createTextNode(key);
        th.appendChild(text);
        row.appendChild(th);
    }
}

function generateTable(table, data) {
    var number = 0;
    for (let element of data) {

        number++;
        let row = table.insertRow();
        let cell = row.insertCell();
        let count = document.createTextNode(number);
        cell.appendChild(count);

        //avartar 
        let image = '<img src="' + element.avatar_url + '"></img>';
        let cell2 = row.insertCell();
        let textAvatar = document.createElement("IMG");
        textAvatar.setAttribute("src", element.avatar_url);
        textAvatar.setAttribute("class", 'profile-img');

        cell2.appendChild(textAvatar);

        //name
        let cell3 = row.insertCell();
        let textName = document.createTextNode(element.login);
        cell3.appendChild(textName);

        //contributions
        let cell4 = row.insertCell();
        let textContirbutions = document.createTextNode(element.contributions);
        cell4.appendChild(textContirbutions);

        //type
        let cell5 = row.insertCell();
        let textType = document.createTextNode(element.type);
        cell5.appendChild(textType);

        //site_admin
        let cell6 = row.insertCell();
        let textSiteAdmin = document.createTextNode(element.site_admin);
        cell6.appendChild(textSiteAdmin);

    }

}

function generateCommitImpactGraph(result) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "user/commit",
        data: {
            repo: result
        },
        dataType: 'json',
        success: function(data) {
            if (data.length < 1) {
                return;
            }

            var xAxis = [];
            var yAxis = [];
            var timeAxis = [];

            data.data.forEach(function(resultValue) {
                xAxis.push((resultValue.sha).slice(-7));
                yAxis.push(resultValue.commit.commitStatus.total);
                timeAxis.push(new Date(resultValue.commit.author.date));
            });

            var impactData = {
                labels: xAxis,
                datasets: [{
                    label: "Commit Impact",
                    backgroundColor: "rgba(255,99,132,0.2)",
                    borderColor: "rgba(255,99,132,1)",
                    borderWidth: 2,
                    hoverBackgroundColor: "rgba(255,99,132,0.4)",
                    hoverBorderColor: "rgba(255,99,132,1)",
                    data: yAxis,
                }]
            };

            var projectionData = {
                labels: xAxis,
                datasets: [{
                    label: "Commit Projection",
                    backgroundColor: "rgba(255,99,132,0.2)",
                    borderColor: "rgba(255,99,132,1)",
                    borderWidth: 2,
                    hoverBackgroundColor: "rgba(255,99,132,0.4)",
                    hoverBorderColor: "rgba(255,99,132,1)",
                    data: timeAxis,
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

            Chart.Line('commitImpact', {
                options: options,
                data: impactData
            });

            Chart.Line('commitProjection', {
                options: options,
                data: projectionData
            });

        }
    });
}

function goToRepositories() {
    emptyState.classList.add('d-none')
    emptyState.classList.remove('d-block')

    resultSection.classList.add('d-block')
    resultSection.classList.remove('d-none')

    searchDiv.classList.remove('d-none')
    searchDiv.classList.add('d-block')


    analyticsSection.classList.remove('d-block')
    analyticsSection.classList.add('d-none');
}