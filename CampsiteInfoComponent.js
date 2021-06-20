import React, { Component } from 'react';
import { Card, CardImg, CardImgOverlay, CardText, CardBody, CardTitle } from 'reactstrap';

class CampsiteInfo extends Component {
    renderCampsite(Campsite) {
        return (<div className="col-md-5 m-1">
            
            <Card>
                <CardImg top src={Campsite.image} alt={Campsite.name} />
                <CardBody>
                    <CardTitle>{Campsite.name}</CardTitle>
                    <CardText>{Campsite.description}</CardText>
                </CardBody>
            </Card>

            </div>
            )
    }
    render() {
        if (this.props.Campsite != null) {
            return <div className="row">  
            {this.renderCampsite(this.props.Campsite)} 
            </div>


        } else {
        return <div />
    }
}
}

//     renderComments(comments) {
//         if(this.props.comments){
//             return ( <div className=”col-md-5 m-1” /> )
//     <h4>Comments</h4>
//     {this.map(comments)}
//     {text}
//     {author, date}
//     {new Intl.DateTimeFormat('en-US', { year: 'numeric', month: 'short', day: '2-digit'}).format(new Date(Date.parse(comment.date)))}
//     </div>
//     }
// }
//     Return (<div />)

export default CampsiteInfo;
