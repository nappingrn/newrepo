import React, { Component } from 'react';
import CampsiteInfo from './CampsiteInfoComponent';
import { Card, CardImg, CardImgOverlay, CardText, CardBody, CardTitle } from 'reactstrap';

class Directory extends Component { // literally the entire page but not the navbar
    
    constructor(props) { // takes props from top level
        super(props);
        this.state = {
            selectedCampsite: null // hold campsite selection
        };
    }

    onCampsiteSelect(campsite) {
        console.log(campsite);
        this.setState({selectedCampsite: campsite}); // set selected campsite
    }

    render() {
        const directory = this.props.campsites.map(campsite => {
            return (
                <div key={campsite.id} className="col-md-5 m-1">
                    <Card onClick={() => this.onCampsiteSelect(campsite)}>
                        <CardImg width="100%" src={campsite.image} alt={campsite.name} />
                        <CardImgOverlay>
                            <CardTitle>{campsite.name}</CardTitle>
                        </CardImgOverlay>
                    </Card>
                </div>
            );
        });

        return (
            
            <div className="container">
                
                <div className="row">
                    {directory}
                </div>

                <h1>test</h1>
                <div>
                    <CampsiteInfo Campsite = {this.state.selectedCampsite} />
                </div>
            </div>
        );
    }
}

export default Directory;
