/**
 * Created by Administrator on 2017/8/5.
 */

function getHTML(data){
    switch (data){
        case 1:
            $('#Main_iframe').attr('src','chart.html');
        case 2:
            $('#Main_iframe').attr('src','form-amazeui.html');
    }
}