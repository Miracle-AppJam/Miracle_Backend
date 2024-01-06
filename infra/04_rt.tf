resource "aws_route_table" "miracle_public_rt" {
  vpc_id = aws_vpc.miracle_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.miracle_igw.id
  }

  tags = {
    Name = "miracle-public-rt"
  }
}

resource "aws_route_table" "miracle_private_a_rt" {
  vpc_id = aws_vpc.miracle_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.miracle_nat_a.id
  }

  tags = {
    Name = "miracle-private-a-rt"
  }
}

resource "aws_route_table" "miracle_private_c_rt" {
    vpc_id = aws_vpc.miracle_vpc.id

    route {
        cidr_block = "0.0.0.0/0"
        nat_gateway_id = aws_nat_gateway.miracle_nat_c.id
    }
}